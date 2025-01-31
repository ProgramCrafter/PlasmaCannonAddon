package com.ice2670.plasmacannon.entity;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Created by Eric C on 3/3/2019.
 */
public class EntityAutoTorpedo extends EntityPlasmaBall
{

    private static final DataParameter<Boolean> INVULNERABLE = EntityDataManager.<Boolean>createKey(net.minecraft.entity.projectile.EntityWitherSkull.class, DataSerializers.BOOLEAN);

    public EntityAutoTorpedo(World worldIn)
    {
        super(worldIn);
        this.setSize(0.1125F, 0.1125F);
    }

    public EntityAutoTorpedo(World worldIn, double x, double y, double z, int powerfactor)
    {
        super(worldIn, x, y, z, powerfactor);
        this.setSize(0.1125F, 0.1125F);
    }

    public static void registerFixesLargePlasmaBall(DataFixer fixer)
    {
        EntityPlasmaBall.registerFixesPlasmaBall(fixer, "PlasmaBall");
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */


    @SideOnly(Side.CLIENT)
    public EntityAutoTorpedo(World worldIn, EntityLivingBase throwerIn, int powerfactor2)
    {
        super(worldIn, throwerIn, powerfactor2);
        this.setSize(0.1125F, 0.1125F);
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    /**
     * Explosion resistance of a block relative to this entity
     */
    public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn)
    {
        float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
        Block block = blockStateIn.getBlock();

        if (this.isInvulnerable() && block.canEntityDestroy(blockStateIn, worldIn, pos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this.thrower, pos, blockStateIn))
        {
            f = Math.min(0.8F, f);
        }

        double d=(double)f;
        float f1;

        f1 = (float) (0.3 * d);

        return f1;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            if (result.entityHit != null)
            {
                result.entityHit.attackEntityFrom(DamageSource.DRAGON_BREATH, 2);


                if (result.entityHit instanceof EntityLivingBase)
                {
                    AxisAlignedBB axis = new AxisAlignedBB(result.entityHit.posX - 2, result.entityHit.posY - 2, result.entityHit.posZ - 2,
                            result.entityHit.posX + 2, result.entityHit.posY + 2, result.entityHit.posZ + 2);
                    List<EntityLivingBase> targets = getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, axis);
                    for (EntityLivingBase mob : targets) {
                        (mob).setHealth((mob).getHealth() - 3);
                        mob.hurtResistantTime = -1;
                    }

                }

                if (result.entityHit instanceof EntityPlayer)
                {

                    AxisAlignedBB axis = new AxisAlignedBB(result.entityHit.posX - 2, result.entityHit.posY - 2, result.entityHit.posZ - 2,
                            result.entityHit.posX + 2, result.entityHit.posY + 2, result.entityHit.posZ + 2);
                    List<EntityLivingBase> targets = getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, axis);
                    for (EntityLivingBase mob : targets) {
                        (mob).setHealth((mob).getHealth() - 3);
                        mob.hurtResistantTime = -1;
                    }

                }
                this.world.newExplosion(this, result.entityHit.posX - 0.1*this.motionX, result.entityHit.posY - 0.1*this.motionY, result.entityHit.posZ - 0.1*this.motionZ, (float)1, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.thrower));
                this.setDead();
            }


        }
    }



    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onHitBlock(IBlockState hitBlock, BlockPos pos)
    {
        if (!hitBlock.getMaterial().isSolid()) {
            // Go through non solid block
            return;
        } else {
            if (hitBlock.getBlockHardness(world,pos)<=4)
            {
            getEntityWorld().destroyBlock(pos, false);
            }
        }
        this.world.newExplosion(this, pos.getX() - 0.3*this.motionX, pos.getY() - 0.3*this.motionY, pos.getZ() - 0.3*this.motionZ, (float)1, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.thrower));
        this.setDead();


    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataManager.register(INVULNERABLE, Boolean.valueOf(false));
    }

    /**
     * Return whether this skull comes from an invulnerable (aura) wither boss.
     */
    public boolean isInvulnerable()
    {
        return ((Boolean)this.dataManager.get(INVULNERABLE)).booleanValue();
    }

    /**
     * Set whether this skull comes from an invulnerable (aura) wither boss.
     */
    public void setInvulnerable(boolean invulnerable)
    {
        this.dataManager.set(INVULNERABLE, Boolean.valueOf(invulnerable));
    }

    protected boolean isFireballFiery()
    {
        return false;
    }


}
