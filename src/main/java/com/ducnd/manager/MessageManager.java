package com.ducnd.manager;

import com.ducnd.model.request.SendMessageRequest;
import com.ducnd.model.response.MessageResponse;
import com.ducnd.model.response.ResponseUtils;
import com.ducnd.security.withoutloginregister.JwtAuthenticationToken;
import com.ducnd.tables.Message;
import com.ducnd.tables.UserProfile;
import com.ducnd.tables.records.MessageRecord;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Lap trinh on 3/28/2018.
 */
@Component
public class MessageManager extends BaseManager {


    public Object sendMessage(SendMessageRequest request) {
        if (request.getReceiverId() == null) {
            return "ban phai truyen len receverId";
        }
        JwtAuthenticationToken token =
                (JwtAuthenticationToken)
                        SecurityContextHolder.getContext()
                                .getAuthentication();
        //username cua thang gui request
        String username = token.getCredentials().getUsername();
        int senderId = dslContext.select(UserProfile.USER_PROFILE.ID)
                .from(UserProfile.USER_PROFILE)
                .where(UserProfile.USER_PROFILE.USERNAME.eq(username))
                .fetchAny()
                .value1();
        MessageRecord record =
                dslContext.insertInto(Message.MESSAGE,
                        Message.MESSAGE.SENDER_ID, Message.MESSAGE.RECEIVER_ID,
                        Message.MESSAGE.CONTENT)
                .values(senderId, request.getReceiverId(), request.getContent())
                .returning().fetchOne();

        return ResponseUtils.getBaseResponse(request);
    }

    public Object messages() {
        JwtAuthenticationToken token =
                (JwtAuthenticationToken)
                        SecurityContextHolder.getContext()
                                .getAuthentication();
        //username cua thang gui request
        String username = token.getCredentials().getUsername();
        int userId = dslContext.select(UserProfile.USER_PROFILE.ID)
                .from(UserProfile.USER_PROFILE)
                .where(UserProfile.USER_PROFILE.USERNAME.eq(username))
                .fetchAny()
                .value1();

        List<MessageResponse> responses =
                dslContext.selectFrom(
                Message.MESSAGE
        )
                .where(
                        Message.MESSAGE.RECEIVER_ID.eq(userId)
                                .or(Message.MESSAGE.SENDER_ID.eq(userId))
                )
                .fetch()
                .map(re->{
                    MessageResponse response = new MessageResponse();
                    response.setContent(re.getContent());
                    response.setSenderId(re.getSenderId());
                    response.setReceiverId(re.getReceiverId());
                    return response;
                });
        return ResponseUtils.getBaseResponse(responses);
    }
}
