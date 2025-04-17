package org.friendship.notify.impl.reaction.dto;
import lombok.Builder;
import lombok.Data;
import org.friendship.notify.RequestNotify;
import org.friendship.notify.annotation.ReactionRequest;

@Data
@Builder
@ReactionRequest
public class ReactionDTO implements RequestNotify
{
    private String sender;
    private String receiver;
    private ReactionType reactionType;

    public enum ReactionType {
        LIKE,
        DISLIKE,
    }
}
