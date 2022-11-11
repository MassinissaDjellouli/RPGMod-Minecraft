package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import com.massinissadjellouli.RPGmod.entities.ModEntities;
import com.massinissadjellouli.RPGmod.objects.NetherPortal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.Map.Entry;

public class InvasionNetherWorldEvent extends WorldEvent {
    List<NetherPortal> netherPortals = new ArrayList<>();
    private final static int SPAWN_DELAY = 1000;
    private int spawnProgress;
    private boolean hasToLoad;
    private CompoundTag nbtToLoad;


    public InvasionNetherWorldEvent() {
        super("Invasion du Nether");
        showTitle = true;
    }

    @Override
    protected void launch() {
        if (hasToLoad) {
            if (level != null) {
                loadNBTPortal(nbtToLoad);
                hasToLoad = false;
            }
            return;
        }
        if(ongoingEventIsStarting()){
            ClientLastTitleReceived.set("Attention! Un event invasion du nether commence!",
                    "Éloignez vous des portails du nether!",
                    ChatFormatting.DARK_RED);
            Map<Axis, List<BlockPos>> axisListMap = netherPortalsInRangeOfPlayer(200);
            long startTime = System.nanoTime();
            fillNetherPortals(axisListMap);
            long endTime = System.nanoTime();
            double duration = (double) (endTime - startTime)/ 1_000_000_000;
            System.out.println("Duration: " + duration + "s");
            netherPortals.forEach(NetherPortal::startSpread);
        }
    }

    @Override
    public void end() {
        netherPortals.forEach(NetherPortal::end);
        super.end();
    }

    @Override
    protected void saveData(CompoundTag nbt) {
        nbt.putInt("spawn_progress", spawnProgress);
        nbt.putInt("nether_portals_size", netherPortals.size());
        for (int i = 0; i < netherPortals.size(); i++) {
            netherPortals.get(i).saveData("nether_portal_" + i, nbt);
        }
    }
    @Override
    protected void loadData(CompoundTag nbt) {
        spawnProgress = nbt.getInt("spawn_progress");
        if(level == null){
            hasToLoad = true;
            nbtToLoad = nbt;
        }
        else{
            loadNBTPortal(nbt);
        }
    }

    private void loadNBTPortal(CompoundTag nbt) {
        for (int i = 0; i < nbt.getInt("nether_portals_size"); i++) {
            netherPortals = List.of(new NetherPortal(nbt,"nether_portal_" + i,level));
        }
    }

    @Override
    protected RPGModWorldEventType getEventType() {
        return RPGModWorldEventType.INVASION_NETHER;
    }

    private void fillNetherPortals(Map<Axis,List<BlockPos>> portalsByAxis) {
        portalsByAxis.forEach(this::addPortals);
    }

    private void addPortals(Axis axis, List<BlockPos> blockPositions) {
        Map<Integer,List<BlockPos>> map = new LinkedHashMap<>();
        fillBlockPosMap(map,blockPositions, axis);
        Map<Integer,Map<BlockPos,Integer>> mapWithSize = getAllMaxYWithSize(mapWithListToQueue(map));
        List<Entry<Integer, Map<BlockPos, Integer>>> entries = new ArrayList<>(mapWithSize.entrySet().stream().toList());
        entries.sort(Comparator.comparingInt(Entry::getKey));
        Map<BlockPos,BlockPos> mapStartEndPos = intMapEntriesToMap(entries);
        List<NetherPortal> netherPortals = mapToPortals(mapStartEndPos, axis);
        this.netherPortals.addAll(netherPortals);
    }

    private List<NetherPortal> mapToPortals(Map<BlockPos, BlockPos> map, Axis axis) {
        List<Entry<BlockPos, BlockPos>> entries = new ArrayList<>(map.entrySet().stream().toList());
        entries.sort(Comparator.comparingInt(entry -> entry.getKey().getY()));
        Queue<Entry<BlockPos,BlockPos>> queue = new ArrayDeque<>(entries);
        List<Entry<BlockPos, BlockPos>> portalStartAndEndPoints = getByAxis(axis, queue, queue.peek(), new ArrayList<>());
        ArrayList<NetherPortal> portalList = new ArrayList<>();
        portalStartAndEndPoints.forEach(
                startEndPoints -> portalList.add(new NetherPortal(startEndPoints.getKey(),startEndPoints.getValue(),level)));
        return portalList;
    }

    private Map<BlockPos, BlockPos> intMapEntriesToMap(List<Entry<Integer, Map<BlockPos, Integer>>> xEntries) {
        Map<BlockPos,BlockPos> map = new LinkedHashMap<>();
        xEntries.forEach(integerMapEntry -> {
            Map<BlockPos, Integer> value = integerMapEntry.getValue();
            value.forEach((endBlockPos, size) -> map.put(endBlockPos.below(size),endBlockPos));
        });
        return map;
    }



    private Map<Integer, Map<BlockPos, Integer>> getAllMaxYWithSize(Map<Integer, Queue<BlockPos>> map) {
        Map<Integer, Map<BlockPos,Integer>> mapWithMinY = new LinkedHashMap<>();
        map.forEach((integer, blockPosQueue) -> {
            mapWithMinY.put(integer, getAllMaxYWithSize(blockPosQueue, new LinkedHashMap<>(),0));
        });
        return mapWithMinY;
    }

    private Map<BlockPos,Integer> getAllMaxYWithSize(Queue<BlockPos> queue, Map<BlockPos,Integer> map, int size) {
        BlockPos current = queue.poll();
        BlockPos next = queue.peek();
        assert current != null;
        if(next == null){
            map.put(current,size);
            return map;
        }
        int difference = next.getY() - current.getY();
        if(difference < 1){
            difference = - next.getY() + current.getY();
        }
        if(difference > 1){
            map.put(current,size);
            return getAllMaxYWithSize(queue,map,0);
        }
        return getAllMaxYWithSize(queue,map,size + 1);
    }

        private <K,V> Map<K,Queue<V>> mapWithListToQueue(Map<K, List<V>> map) {
        LinkedHashMap<K,Queue<V>> newMap = new LinkedHashMap<>();
        map.forEach((k, v) -> newMap.put(k,new ArrayDeque<>(v)));
        return newMap;
    }

    private void fillBlockPosMap(Map<Integer, List<BlockPos>> map, List<BlockPos> blockPositions, Axis axis) {
        blockPositions.forEach(blockPos -> {
            addToOrInitMap(map,blockPos.get(axis),new BlockPos(blockPos));
        });
    }

    private List<Entry<BlockPos,BlockPos>> getByAxis(Axis axis,
                                                     Queue<Entry<BlockPos,BlockPos>> blockPosQueue,
                                                     Entry<BlockPos,BlockPos> currentBeginingEntry,
                                                     List<Entry<BlockPos,BlockPos>> list) {
        Entry<BlockPos, BlockPos> poll = blockPosQueue.poll();
        Entry<BlockPos, BlockPos> peek = blockPosQueue.peek();
        if(peek == null){
            list.add(currentBeginingEntry);
            return list;
        }
        assert poll != null;
        BlockPos current = poll.getKey();
        BlockPos next = peek.getKey();
        int difference = next.get(axis) - current.get(axis);
        if(difference < 1){
            difference = -next.get(axis) + current.get(axis)  ;
        }
        if(difference > 1){
            list.add(currentBeginingEntry);
            return getByAxis(axis,blockPosQueue,blockPosQueue.peek(),list);
        }
        currentBeginingEntry.setValue(peek.getValue());
        return getByAxis(axis,blockPosQueue,currentBeginingEntry,list);
    }

    private <K, V> void addToOrInitMap(Map<K, List<V>> map, K key, V value) {
        if (map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<V> list = new ArrayList<>();
            list.add(value);
            map.put(key, list);
        }
    }
    @Override
    protected void tick() {
        if (hasToLoad) {
            if (level != null) {
                loadNBTPortal(nbtToLoad);
                hasToLoad = false;
            }else {
                return;
            }
        }
        spawnProgress++;
        netherPortals.forEach(NetherPortal::tick);
        if (spawnProgress < SPAWN_DELAY) {
            return;
        }
        netherPortals.forEach(netherPortal -> {
            for (int i = 0; i < netherPortal.size() / 2; i++) {
                int neg1 = new Random().nextInt(2) == 1 ? -1 : 1;
                int neg2 = new Random().nextInt(2) == 1 ? -1 : 1;
                getRandomEntity().spawn(level, null, player,
                        netherPortal.middle().above(8)
                                .north(neg1 * (new Random().nextInt(netherPortal.length()) + netherPortal.length() / 2))
                                .east(neg2 * (new Random().nextInt(netherPortal.length()) + netherPortal.length() / 2))
                        , MobSpawnType.EVENT, false, false);
            }
        });
        spawnProgress = 0;
    }



    private EntityType<?> getRandomEntity() {
        int random = new Random().nextInt(100);
        int randomEntity;
        if (random < 10) {
            randomEntity = 1;
        } else if (random < 30) {
            randomEntity = 2;
        } else if (random < 45) {
            randomEntity = 3;
        } else if (random < 75) {
            randomEntity = 4;
        } else if (random < 80) {
            randomEntity = 5;
        } else if (random < 98) {
            randomEntity = 6;
        } else {
            randomEntity = 7;
        }

        return switch (randomEntity) {
            case 1 -> EntityType.BLAZE;
            case 2 -> EntityType.MAGMA_CUBE;
            case 3 -> EntityType.WITHER_SKELETON;
            case 4 -> ModEntities.GOBLIN.get();
            case 5 -> EntityType.ZOGLIN;
            case 6 -> EntityType.ZOMBIFIED_PIGLIN;
            case 7 -> EntityType.GHAST;
            default -> EntityType.SKELETON;
        };
    }

    @Override
    protected int[] getEventRange() {
        return new int[]{2000, 4000};
    }

    private Map<Axis,List<BlockPos>> netherPortalsInRangeOfPlayer(int range) {
        if (range > 350) {
            throw new IllegalArgumentException("Range is too big.");
        }
        final int SEARCH_HEIGHT = 20;
        BlockPos blockPos = player.blockPosition();
        BlockPos beginingBP = blockPos.above(SEARCH_HEIGHT).east(range).south(range);
        BlockPos endBP = blockPos.below(SEARCH_HEIGHT).west(range).north(range);
        Map<Axis,List<BlockPos>> portalsByAxis = new LinkedHashMap<>();
        BlockPos.betweenClosedStream(new AABB(beginingBP, endBP)).forEach(
                blockPosition -> {
                    if (level.getBlockState(blockPosition).getBlock().getName().equals(Blocks.NETHER_PORTAL.getName())) {
                        BlockState blockState = level.getBlockState(blockPosition);
                        Axis axis = blockState.getValue(NetherPortalBlock.AXIS);
                        addToOrInitMap(portalsByAxis, axis,new BlockPos(blockPosition));
                    }
                });
        return portalsByAxis;
    }
}
