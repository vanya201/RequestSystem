package org.common.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reply_comments")
public class ReplyComment extends Comment {
    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;
}