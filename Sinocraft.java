package com.sinocraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.sinocraft.plants.RiceCrop;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



    @Mod(modid = Sinocraft.MODID, name = Sinocraft.NAME, version = Sinocraft.VERSION)
public class Sinocraft {
    //basic	
	public static final String MODID = "Sinocraft";
	public static final String NAME = "Sinocraft";
	public static final String VERSION = "0.0.1";
	
	public static CreativeTabs sinocraftTab; 
	
	//Plants
	public static ItemSeeds riceSeeds ;
	public static RiceCrop riceCrop;
	public static ItemFood rice;
	
	//Foods
	
	
	@Instance(value = Sinocraft.MODID)
	public static Sinocraft instance;
	
	@SidedProxy(clientSide = "com.sinocraft.client.ClientProxy", serverSide = "com.sinocraft.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	//CreativeTabs
	sinocraftTab = new CreativeTabs("Sinocraft") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
			return Items.flower_pot;//要改	
			}
		};	
		
	//Plants 
	  riceCrop = new RiceCrop();
	  GameRegistry.registerBlock(riceCrop, "riceCrop");
	  
      riceSeeds = new ItemSeeds(riceCrop, Blocks.farmland);
	  riceSeeds.setTextureName("sinocraft:rice_seeds");
	  riceSeeds.setUnlocalizedName("riceSeeds");
	  riceSeeds.setCreativeTab(sinocraftTab);
	  GameRegistry.registerItem(riceSeeds, "riceSeeds");
	
	  rice = new ItemFood(1, false);
	  rice.setTextureName("sinocraft:rice");
	  rice.setUnlocalizedName("rice");
	  rice.setCreativeTab(sinocraftTab);
	  GameRegistry.registerItem(rice, "rice");
	}
	 
	@EventHandler 
	public void Init(FMLInitializationEvent event) {
		//add the riceSeeds to grass
	    MinecraftForge.addGrassSeed(new ItemStack(riceSeeds), 10);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	
	
	
}
