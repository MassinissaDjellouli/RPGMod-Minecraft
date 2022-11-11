package com.massinissadjellouli.RPGmod.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

public class NetherPortal {
    private ServerLevel level;
    private BlockPos portalStart;
    private BlockPos portalEnd;
    private BlockPos portalFrameStart;
    private BlockPos portalFrameEnd;
    private boolean isSpreading;
    private int spreadRadius;
    private int spreadProgress;
    private final static int SPREAD_RADIUS_MAX = 30;
    private final static int SPREAD_HEIGHT_MAX = 10;
    private final static int SPREAD_PROGRESS_DURATION = 100;

    public NetherPortal(BlockPos portalStart, BlockPos portalEnd, ServerLevel level) {
        this.portalStart = portalStart;
        this.portalEnd = portalEnd;
        this.portalFrameStart = getPortalFrameStart();
        this.portalFrameEnd = getPortalFrameEnd();
        this.isSpreading = false;
        this.spreadRadius = 0;
        this.level = level;
    }



    private BlockPos getPortalFrameEnd() {
        return switch (direction()) {
            case "Z" -> new BlockPos(portalEnd.getX(), portalEnd.getY() + 1, portalEnd.getZ() + 1);
            case "X" -> new BlockPos(portalEnd.getX() + 1, portalEnd.getY() + 1, portalEnd.getZ());
            default -> null;
        };
    }

    private BlockPos getPortalFrameStart() {
        return switch (direction()) {
            case "Z" -> new BlockPos(portalStart.getX(), portalStart.getY() - 1, portalStart.getZ() - 1);
            case "X" -> new BlockPos(portalStart.getX() - 1, portalStart.getY() - 1, portalStart.getZ());
            default -> null;
        };
    }

    public String direction() {
        if (portalStart.getX() == portalEnd.getX()) {
            return "Z";
        }
        if (portalStart.getZ() == portalEnd.getZ()) {
            return "X";
        }
        return null;
    }

    public int size() {
        return length() * height();
    }

    public int length() {
        return switch (direction()) {
            case "X" -> portalEnd.getX() - portalStart.getX();
            case "Z" -> portalEnd.getZ() - portalStart.getZ();
            default -> 0;
        };
    }

    public int height() {
        return portalEnd.getY() - portalStart.getY();
    }

    public void startSpread() {
        this.isSpreading = true;
    }

    public void end() {
        this.isSpreading = false;
        this.spreadRadius = 0;
    }

    public void tick() {
        if (!isSpreading) {
            return;
        }
        if (spreadProgress < SPREAD_PROGRESS_DURATION) {
            spreadProgress++;
            return;
        }
        if (spreadRadius < SPREAD_RADIUS_MAX) {
            spreadRadius++;
            spreadProgress = 0;
            spread();
            return;
        }
        end();
    }

    private void spread() {
        if(level == null){
            return;
        }
        if (middle() == null) {
            return;
        }
        BlockPos beginingBP = middle()
                .above(Math.min(spreadRadius, SPREAD_HEIGHT_MAX))
                .north(spreadRadius)
                .west(spreadRadius);
        BlockPos endBP = middle()
                .below(Math.min(spreadRadius, SPREAD_HEIGHT_MAX))
                .south(spreadRadius)
                .east(spreadRadius);
        BlockPos.betweenClosedStream(new AABB(beginingBP, endBP))
                .filter(blockPos -> {
                    Block block = level.getBlockState(blockPos).getBlock();
                    return isSpreadableBlock(block);
                })
                .forEach(blockPosition -> {
                    if (isAir(level.getBlockState(blockPosition).getBlock())) {
                        if (isSameBlock(level.getBlockState(blockPosition.below()).getBlock(), Blocks.NETHERRACK)
                        || isSameBlock(level.getBlockState(blockPosition.below()).getBlock(), Blocks.SOUL_SAND)
                        || isSameBlock(level.getBlockState(blockPosition.below()).getBlock(), Blocks.SOUL_SOIL)) {
                            level.setBlockAndUpdate(blockPosition, Blocks.FIRE.defaultBlockState());
                        }
                            return;
                    }
                    level.setBlockAndUpdate(blockPosition, switch (new Random().nextInt(15)) {
                        case 1 -> Blocks.SOUL_SOIL.defaultBlockState();
                        case 2, 3 -> Blocks.SOUL_SAND.defaultBlockState();
                        case 4, 5 -> Blocks.MAGMA_BLOCK.defaultBlockState();
                        case 6, 7, 8 -> Blocks.NETHER_BRICKS.defaultBlockState();
                        default -> Blocks.NETHERRACK.defaultBlockState();
                    });
                });
    }

    private boolean isSameBlock(Block block1, Block block2) {
        return block1.getName().equals(block2.getName());
    }

    private boolean isAir(Block block) {
        return isSameBlock(block, Blocks.AIR);
    }

    private boolean isSpreadableBlock(Block block) {
        if (isAir(block) && new Random().nextInt(500) == 1) {
            return true;
        }
        for (Block unspreadableBlock : unspreadableBlocks()) {
            if (isSameBlock(block, unspreadableBlock)) {
                return false;
            }
        }
        return true;
    }

    public BlockPos middle() {
        if (portalFrameStart == null || portalFrameEnd == null) {
            return null;
        }
        return switch (direction()) {
            case "X" -> new BlockPos(
                    portalFrameStart.getX() + (portalFrameEnd.getX() - portalFrameStart.getX()) / 2
                    , portalFrameStart.getY(),
                    portalFrameStart.getZ());
            case "Z" -> new BlockPos(
                    portalFrameStart.getX(),
                    portalFrameStart.getY(),
                    portalFrameStart.getZ() + (portalFrameEnd.getZ() - portalFrameStart.getZ()) / 2);
            default -> null;
        };
    }

    private List<Block> unspreadableBlocks() {
        return List.of(
                Blocks.OBSIDIAN,
                Blocks.AIR,
                Blocks.CAVE_AIR,
                Blocks.VOID_AIR,
                Blocks.BEDROCK,
                Blocks.NETHER_PORTAL,
                Blocks.END_PORTAL,
                Blocks.END_PORTAL_FRAME,
                Blocks.END_GATEWAY,
                Blocks.NETHERRACK,
                Blocks.NETHER_BRICKS,
                Blocks.NETHER_GOLD_ORE,
                Blocks.NETHER_QUARTZ_ORE,
                Blocks.NETHER_WART_BLOCK,
                Blocks.NETHER_SPROUTS,
                Blocks.NETHER_BRICK_FENCE,
                Blocks.NETHER_BRICK_SLAB,
                Blocks.NETHER_BRICK_STAIRS,
                Blocks.NETHER_BRICK_WALL,
                Blocks.NETHER_WART_BLOCK,
                Blocks.NETHER_GOLD_ORE,
                Blocks.SOUL_CAMPFIRE,
                Blocks.SOUL_FIRE,
                Blocks.SOUL_LANTERN,
                Blocks.SOUL_SAND,
                Blocks.SOUL_SOIL,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH,
                Blocks.WATER,
                Blocks.LAVA,
                Blocks.FIRE,
                Blocks.CRYING_OBSIDIAN,
                Blocks.BUBBLE_COLUMN,
                Blocks.CRIMSON_BUTTON,
                Blocks.CRIMSON_DOOR,
                Blocks.CRIMSON_FENCE,
                Blocks.CRIMSON_FENCE_GATE,
                Blocks.CRIMSON_PRESSURE_PLATE,
                Blocks.CRIMSON_SIGN,
                Blocks.CRIMSON_TRAPDOOR,
                Blocks.CRIMSON_WALL_SIGN,
                Blocks.CRIMSON_FUNGUS,
                Blocks.CRIMSON_HYPHAE,
                Blocks.CRIMSON_NYLIUM,
                Blocks.CRIMSON_PLANKS,
                Blocks.CRIMSON_ROOTS,
                Blocks.CRIMSON_SLAB,
                Blocks.CRIMSON_STAIRS,
                Blocks.CRIMSON_STEM,
                Blocks.WARPED_BUTTON,
                Blocks.WARPED_DOOR,
                Blocks.WARPED_FENCE,
                Blocks.WARPED_FENCE_GATE,
                Blocks.WARPED_PRESSURE_PLATE,
                Blocks.WARPED_SIGN,
                Blocks.WARPED_TRAPDOOR,
                Blocks.WARPED_WALL_SIGN,
                Blocks.WARPED_WART_BLOCK,
                Blocks.WARPED_ROOTS,
                Blocks.WARPED_STEM,
                Blocks.WARPED_HYPHAE,
                Blocks.WARPED_NYLIUM,
                Blocks.WARPED_PLANKS,
                Blocks.WARPED_SLAB,
                Blocks.WARPED_STAIRS,
                Blocks.WARPED_FUNGUS,
                Blocks.MAGMA_BLOCK
        );

    }

    public void saveData(String key, CompoundTag nbt) {
        nbt.putInt(key + "_spread_radius", spreadRadius);
        nbt.putInt(key + "_spread_progres", spreadProgress);
        saveBlockPos(nbt, key + "_portal_frameStart", portalFrameStart);
        saveBlockPos(nbt, key + "_portal_frameEnd", portalFrameEnd);
        saveBlockPos(nbt, key + "_portal_start", portalStart);
        saveBlockPos(nbt, key + "_portal_end", portalEnd);
        nbt.putBoolean(key + "_is_spreading", isSpreading);
    }

    private void saveBlockPos(CompoundTag nbt, String key, BlockPos bp) {
        if (bp != null) {
            nbt.putInt(key + "_x", bp.getX());
            nbt.putInt(key + "_y", bp.getY());
            nbt.putInt(key + "_z", bp.getZ());
        }
    }
    public NetherPortal(CompoundTag nbt, String key, ServerLevel level) {
        spreadRadius = nbt.getInt(key + "_spread_radius");
        spreadProgress = nbt.getInt(key + "_spread_progres");
        portalFrameStart = loadBlockPos(nbt, key + "_portal_frameStart");
        portalFrameEnd = loadBlockPos(nbt, key + "_portal_frameEnd");
        portalStart = loadBlockPos(nbt, key + "_portal_start");
        portalEnd = loadBlockPos(nbt, key + "_portal_end");
        isSpreading = nbt.getBoolean(key + "_is_spreading");
        this.level = level;
    }

    private BlockPos loadBlockPos(CompoundTag nbt, String key) {
        if (nbt.contains(key + "_x") && nbt.contains(key + "_y") && nbt.contains(key + "_z")) {
            int x = nbt.getInt(key + "_x");
            int y = nbt.getInt(key + "_y");
            int z = nbt.getInt(key + "_z");
            return new BlockPos(x, y, z);
        }
        else {
            return null;
        }
    }
}
