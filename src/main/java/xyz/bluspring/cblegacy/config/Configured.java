package xyz.bluspring.cblegacy.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Configured
{

	String category();

}
