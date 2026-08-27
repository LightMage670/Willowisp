package lightmage670.willowisp;

import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.server.command.ServerCommandSource;

// #region suggestion_provider
public class BloodSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context,
        SuggestionsBuilder builder) throws CommandSyntaxException {
        String[] bloodTypes = {"mortal","magic","divine","god","vampire","bad","sculk","ink","none"};

        for (String bloodType : bloodTypes) {
			builder.suggest(bloodType);
		}
        return builder.buildFuture();
    }	
}