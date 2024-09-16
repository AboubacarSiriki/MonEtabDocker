package ci.digitalacademy.monetab.utils;

import com.github.slugify.Slugify;

import java.util.UUID;

public final class SlugifyUtils {

    public SlugifyUtils(){

    }

    public static String generated( String input){
        String value = String.format("%s, %s", input, UUID.randomUUID());

        final Slugify slg = Slugify.builder().underscoreSeparator(true).build();
        final String result = slg.slugify(value);
        return slg.slugify(value);
    }


}
