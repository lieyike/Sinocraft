package com.sinocraft.plants;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.ForgeEventFactory;

import com.sinocraft.Sinocraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RiceCrop extends BlockCrops{
	
	private IIcon[] RiceCropIIcon;
	
	
	public RiceCrop() {
	super();	
	float f = 0.5F;
	this.setBlockName("riceCrop");
    this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
	this.setTickRandomly(true);
	this.setCreativeTab(null);
	this.setHardness(0.0F);
	this.setStepSound(soundTypeGrass);
	this.setBlockTextureName("sinocraft:rice_crop");
	}
	
	
	//要好好更改func_149864
	//该方法是用了判断邻接方块onNeighborBlockChange
	private float func_149864_n(World p_149864_1_, int p_149864_2_, int p_149864_3_, int p_149864_4_)
    {
        float f = 1.0F;
        Block block = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        Block block1 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        Block block2 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        Block block3 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        Block block4 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        Block block5 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        Block block6 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        Block block7 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        boolean flag = block2 == this || block3 == this;
        boolean flag1 = block == this || block1 == this;
        boolean flag2 = block4 == this || block5 == this || block6 == this || block7 == this;

        for (int l = p_149864_2_ - 1; l <= p_149864_2_ + 1; ++l)
        {
            for (int i1 = p_149864_4_ - 1; i1 <= p_149864_4_ + 1; ++i1)
            {
                float f1 = 0.0F;

                if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).canSustainPlant(p_149864_1_, l, p_149864_3_ - 1, i1, ForgeDirection.UP, this))
                {
                    f1 = 1.0F;

                    if (p_149864_1_.getBlock(l, p_149864_3_ - 1, i1).isFertile(p_149864_1_, l, p_149864_3_ - 1, i1))
                    {
                        f1 = 3.0F;
                    }
                }

                if (l != p_149864_2_ || i1 != p_149864_4_)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        if (flag2 || flag && flag1)
        {
            f /= 2.0F;
        }

        return f;
    }
	
	
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
    {
        super.updateTick(world, x, y, z, random);

        if (world.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int l = world.getBlockMetadata(x, y, z);

            if (l < 6)
            {
                float f = this.func_149864_n(world, x, y, z);

                if (random.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    ++l;
                    world.setBlockMetadataWithNotify(x, y, z, l, 2);
                }
            }
        }
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata < 0 || metadata > 6)
        {
            metadata = 6;
        }

        return this.RiceCropIIcon[metadata];
    }
	
	@Override
	public void registerBlockIcons(IIconRegister riceCropIIconRegister)
    {
        this.RiceCropIIcon = new IIcon[7];

        for (int i = 0; i < this.RiceCropIIcon.length; ++i)
        {
            this.RiceCropIIcon[i] = riceCropIIconRegister.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }
	
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune) {
		switch (metadata) {
		case 0:
			return Sinocraft.riceSeeds;
		case 7: 
			return Sinocraft.rice;
		default : return null;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Sinocraft.riceSeeds;
    }
	
	@Override
	protected Item func_149866_i()
    {
        return Sinocraft.riceSeeds;
    }
	
	@Override
	protected Item func_149865_P()
    {
        return Sinocraft.rice;
    }
	
	//这个方法是中用了super.getDrops 可能是导致不覆盖func_149866_i()方法时会出现植物最后小麦种子的原因
	//尝试解决方法就是覆盖func_149866_i() 或者不要继承blockCrops而从block继承来写
	@Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= 6)
        {
            for (int i = 0; i < 1; ++i)
            {
                if (world.rand.nextInt(15) <= metadata)
                {
                    ret.add(new ItemStack(Sinocraft.riceSeeds, 1, 0));
                }
            }
        }

        return ret;
    }
	
	
	

	
	//还有几个问题教程上idPicked没有解决
	/*这里是wiki上教程的代码可能不能用	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if(world.getBlockMetadata(x, y, z) == 1) {
		    return;
	    }
		if(random.nextInt(isFertile(world, x, y-1, z) ? 12 : 25) != 0) {
			return;
		}
	    world.setBlockMetadataWithNotify(x, y, z, 1, 1);//注意 x y z metadata flag 
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x,
			int y, int z) {
		return null;
	}
	
	@Override
	public int getRenderType() {
		return 6;
	}
	
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	*/
}