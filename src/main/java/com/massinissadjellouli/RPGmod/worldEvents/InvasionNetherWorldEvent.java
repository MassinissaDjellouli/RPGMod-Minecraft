package com.massinissadjellouli.RPGmod.worldEvents;

import com.massinissadjellouli.RPGmod.client.ClientLastTitleReceived;
import com.massinissadjellouli.RPGmod.entities.ModEntities;
import com.massinissadjellouli.RPGmod.objects.NetherPortal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.*;
import java.util.stream.Stream;

public class InvasionNetherWorldEvent extends WorldEvent {
    List<NetherPortal> netherPortals = new ArrayList<>();
    private final static int SPAWN_DELAY = 1000;
    private int spawnProgress;


    public InvasionNetherWorldEvent() {
        super("Invasion du Nether");
        showTitle = true;
    }

    @Override
    protected void launch() {
        ClientLastTitleReceived.set("Attention! Un event invasion du nether commence!",
                "Éloignez vous des portails du nether!",
                ChatFormatting.DARK_RED);
        List<BlockPos> blockPos = netherPortalsInRangeOfPlayer(200);
        netherPortals = convertBlockPosListToNetherPortals(blockPos);
        netherPortals.forEach(NetherPortal::startSpread);
    }

    @Override
    public void end() {
        netherPortals.forEach(NetherPortal::end);
        super.end();
    }

    private List<NetherPortal> convertBlockPosListToNetherPortals(List<BlockPos> blockPos) {
        Map<Integer, List<BlockPos>> xMap = new HashMap<>();
        Map<Integer, List<BlockPos>> zMap = new HashMap<>();
        Set<Integer> zCoordsRemovedFromX = new HashSet<>();
        for (BlockPos pos : blockPos) {
            addToOrInitMap(xMap, pos.getX(), pos);
            addToOrInitMap(zMap, pos.getZ(), pos);
        }
        xMap.forEach((x, bp) -> {
            ArrayList<BlockPos> filteredX = new ArrayList<>();
            bp.forEach(blockPosition -> {
                if (filteredX.stream().map(Vec3i::getZ).toList().contains(blockPosition.getZ())) {
                    return;
                }
                filteredX.add(blockPosition);
            });
            xMap.put(x, filteredX);
        });
        ArrayList<Integer> xKeysToRemove = new ArrayList<>();
        xMap.forEach((x, bp) -> {
            if (bp.size() == 1) {
                zCoordsRemovedFromX.add(bp.get(0).getZ());
                xKeysToRemove.add(x);
            }
        });
        xKeysToRemove.forEach(xMap::remove);
        ArrayList<Integer> zKeysToRemove = new ArrayList<>();
        zMap.forEach((z, bp) -> {
            if (!zCoordsRemovedFromX.contains(z)) {
                zKeysToRemove.add(z);
            }
        });
        zKeysToRemove.forEach(zMap::remove);
        List<NetherPortal> netherPortals = new ArrayList<>();
        xMap.forEach((x, bp) -> {
            int maxZ = bp.stream().max(Comparator.comparingInt(Vec3i::getZ)).get().getZ();
            int minZ = bp.stream().min(Comparator.comparingInt(Vec3i::getZ)).get().getZ();

            int minY = blockPos.stream().filter(
                    blockPosition -> blockPosition.getZ() == maxZ && blockPosition.getX() == x
            ).min(Comparator.comparingInt(Vec3i::getY)).get().getY();

            int maxY = blockPos.stream().filter(
                    blockPosition -> blockPosition.getZ() == maxZ && blockPosition.getX() == x
            ).max(Comparator.comparingInt(Vec3i::getY)).get().getY();

            netherPortals.add(new NetherPortal(new BlockPos(x, minY, minZ), new BlockPos(x, maxY, maxZ),level));
        });
        zMap.forEach((z, bp) -> {
            int maxX = bp.stream().max(Comparator.comparingInt(Vec3i::getX)).get().getX();
            int minX = bp.stream().min(Comparator.comparingInt(Vec3i::getX)).get().getX();

            ;
            int minY = blockPos.stream().filter(
                    blockPosition -> blockPosition.getX() == maxX && blockPosition.getZ() == z
            ).min(Comparator.comparingInt(Vec3i::getY)).get().getY();
            int maxY = blockPos.stream().filter(blockPosition -> blockPosition.getX() == maxX && blockPosition.getZ() == z
            ).max(Comparator.comparingInt(Vec3i::getY)).get().getY();
            netherPortals.add(new NetherPortal(new BlockPos(minX, minY, z), new BlockPos(maxX, maxY, z),level));
        });
        return netherPortals;
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
        spawnProgress++;
        netherPortals.forEach(NetherPortal::tick);
        if(spawnProgress < SPAWN_DELAY){
            return;
        }
        netherPortals.forEach(netherPortal -> {
        for (int i = 0; i < netherPortal.size() / 2; i++) {
            int neg1 = new Random().nextInt(2) == 1?-1:1;
            int neg2 = new Random().nextInt(2) == 1?-1:1;
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
        if(random < 10) {
            randomEntity = 1;
        } else if(random < 30) {
            randomEntity = 2;
        } else if(random < 45) {
            randomEntity = 3;
        } else if(random < 75) {
            randomEntity = 4;
        }else if(random < 80) {
            randomEntity = 5;
        }else if(random < 98) {
            randomEntity = 6;
        } else{
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

    private List<BlockPos> netherPortalsInRangeOfPlayer(int range) {
        if (range > 350) {
            throw new IllegalArgumentException("Range is too big.");
        }
        final int SEARCH_HEIGHT = 20;
        BlockPos blockPos = player.blockPosition();
        BlockPos beginingBP = blockPos.above(SEARCH_HEIGHT).east(range).south(range);
        BlockPos endBP = blockPos.below(SEARCH_HEIGHT).west(range).north(range);
        ArrayList<BlockPos> portals = new ArrayList<>();
        BlockPos.betweenClosedStream(new AABB(beginingBP, endBP)).forEach(
                blockPosition -> {
                    if (level.getBlockState(blockPosition).getBlock().getName().equals(Blocks.NETHER_PORTAL.getName())) {
                        portals.add(new BlockPos(blockPosition));
                    }
                });
        return portals;
    }
}
