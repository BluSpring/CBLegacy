package xyz.bluspring.cblegacy.render.patterns;

import java.util.WeakHashMap;

import xyz.bluspring.cblegacy.core.ClientSide;
import xyz.bluspring.cblegacy.interfaces.IPatternItem;
import xyz.bluspring.cblegacy.render.BaseSmartModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PrintSmartModel extends BaseSmartModel
{

	WeakHashMap<ItemStack, PrintBaked> cache = new WeakHashMap<ItemStack, PrintBaked>();

	final IPatternItem item;
	final String name;

	public PrintSmartModel(
			final String name,
			final IPatternItem item )
	{
		this.name = name;
		this.item = item;
	}

	@Override
	public IBakedModel handleItemState(
			final IBakedModel originalModel,
			final ItemStack stack,
			final World world,
			final EntityLivingBase entity )
	{
		if ( ClientSide.instance.holdingShift() )
		{
			PrintBaked npb = cache.get( stack );

			if ( npb == null )
			{
				cache.put( stack, npb = new PrintBaked( name, item, stack ) );
			}

			return npb;
		}

		return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel( new ModelResourceLocation( "chiselsandbits:" + name + "_written", "inventory" ) );
	}

}
