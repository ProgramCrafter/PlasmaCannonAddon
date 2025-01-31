package com.ice2670.plasmacannon.init;

import com.ice2670.plasmacannon.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class BlockInit
{
    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block BLOCK_PLASMAGENERATOR = new BlockBase("block_plasmagenerator", Material.IRON);

    public static final Block BLOCK_PLASMA_CANNON = new BlockPlasmaCannon("plasma_cannon");

    public static final Block BLOCK_ADVCOMPOSITEARMOR = new BlockAdvCompositeArmor("block_advcompositearmor");

    public static final Block BLOCK_TORPEDOLAUNCHER = new BlockTorpedoLauncher("block_torpedo_launcher");

    public static final Block BLOCK_COMPOSITEARMOR = new BlockCompositeArmor("block_compositearmor");

    public static final Block BLOCK_SHIPARMOR = new BlockShipArmor("block_shiparmor");

    public static final Block BPC = new BlockPC("bpc");

    public static final Block BLOCK_BROKENACA1 = new BlockBrokenACA1("block_brokenaca1");

    public static final Block BLOCK_BROKENACA2 = new BlockBrokenACA2("block_brokenaca2");

    public static final Block BLOCK_BROKENACA3 = new BlockBrokenACA3("block_brokenaca3");

    public static final Block BLOCK_BROKENCA = new BlockBrokenCA("block_brokenca");

    public static final Block BLOCK_BROKENTG = new BlockBrokenTG("block_brokentg");

    public static final Block BLOCK_FASTTORPEDO = new BlockTorpedo("block_fasttorpedo", 3);

    public static final Block BLOCK_HIGHEXPLOSIVETORPEDO = new BlockTorpedo("block_highexplosivetorpedo", 6);

    public static final Block FAKETORPEDO = new BlockPC("faketorpedo");
}
