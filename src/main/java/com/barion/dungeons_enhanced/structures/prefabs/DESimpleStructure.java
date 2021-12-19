package com.barion.dungeons_enhanced.structures.prefabs;

import com.barion.dungeons_enhanced.DEStructures;
import com.legacy.structure_gel.api.config.StructureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class DESimpleStructure extends DEBaseStructure {

    public DESimpleStructure(StructureConfig config, boolean generateNearSpawn, DEPiece... resources){
        super(config, GenerationType.onGround, generateNearSpawn, resources);
    }

    public DESimpleStructure(StructureConfig config, BlockPos offset, boolean generateNearSpawn, DEPiece... resources){
        super(config, GenerationType.onGround, offset, generateNearSpawn, resources);
    }

    public static class Piece extends DEBaseStructure.Piece {
        public Piece(StructureManager structureManager, ResourceLocation templateName, BlockPos pos, Rotation rotation, int componentType) {
            super(DEStructures.RuinedStructure.getPieceType(), componentType, structureManager, templateName, pos, rotation);
        }

        public Piece(StructureManager structureManager, ResourceLocation templateName, BlockPos pos, Rotation rotation) {
            this(structureManager, templateName, pos, rotation, 0);
        }

        public Piece(ServerLevel level, CompoundTag nbt) {
            super(DEStructures.RuinedStructure.getPieceType(), nbt, level);
        }
    }
}