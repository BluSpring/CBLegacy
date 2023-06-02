package xyz.bluspring.cblegacy.core.api;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import xyz.bluspring.cblegacy.api.KeyBindingContext;
import xyz.bluspring.cblegacy.client.ModConflictContext;
import xyz.bluspring.cblegacy.core.Log;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class IMCHandlerKeyBindingAnnotations implements IMCMessageHandler
{

	@Override
	public void excuteIMC(
			final IMCMessage message )
	{
		try
		{
			boolean found = false;
			Class<?> itemClass;
			Annotation annotation;
			List<String> conflictContextNames;
			ResourceLocation regName;

			for ( Item item : Item.REGISTRY )
			{
				regName = item.getRegistryName();

				if ( regName == null || !regName.getResourceDomain().equals( message.getSender() ) )
				{
					continue;
				}

				itemClass = item.getClass();

				while ( itemClass != Item.class )
				{
					if ( itemClass.isAnnotationPresent( KeyBindingContext.class ) )
					{
						annotation = itemClass.getAnnotation( KeyBindingContext.class );

						if ( annotation instanceof KeyBindingContext )
						{
							conflictContextNames = Arrays.asList( ( (KeyBindingContext) annotation ).value() );

							for ( ModConflictContext conflictContext : ModConflictContext.values() )
							{
								if ( conflictContextNames.contains( conflictContext.getName() ) )
								{
									conflictContext.setItemActive( item );
									found = true;
								}
							}
						}
					}

					itemClass = itemClass.getSuperclass();
				}				
			}

			if ( !found )
			{
				throw new RuntimeException( "No item classes were found with a KeyBindingContext annotation that applies to sub-classes. Add one with 'applyToSubClasses = true' to do so." );
			}
		}
		catch ( final Throwable e )
		{
			Log.logError( "IMC initkeybindingannotations From " + message.getSender(), e );
		}
	}
}
