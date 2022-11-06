package com.massinissadjellouli.RPGmod.objects;

import net.minecraft.core.BlockPos;

public class NetherPortal {
    private BlockPos portalStart;
    private BlockPos portalEnd;
    private BlockPos portalFrameStart;
    private BlockPos portalFrameEnd;

    public NetherPortal(BlockPos portalStart, BlockPos portalEnd) {
        this.portalStart = portalStart;
        this.portalEnd = portalEnd;
        this.portalFrameStart = getPortalFrameStart();
        this.portalFrameEnd = getPortalFrameEnd();
    }

    private BlockPos getPortalFrameEnd() {
        if(portalStart.getX() == portalEnd.getX()){
            return new BlockPos(portalEnd.getX(),portalEnd.getY() + 1,portalEnd.getZ() + 1);
        }
        if(portalStart.getZ() == portalEnd.getZ()){
            return new BlockPos(portalEnd.getX() + 1,portalEnd.getY() + 1,portalEnd.getZ());
        }
        return null;
    }

    private BlockPos getPortalFrameStart() {
        if(portalStart.getX() == portalEnd.getX()){
            return new BlockPos(portalStart.getX(),portalStart.getY() - 1,portalStart.getZ() - 1);
        }
        if(portalStart.getZ() == portalEnd.getZ()){
            return new BlockPos(portalStart.getX() - 1,portalStart.getY() - 1,portalStart.getZ());
        }
        return null;
    }
}
