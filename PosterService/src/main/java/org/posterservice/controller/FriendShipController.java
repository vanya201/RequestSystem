package org.posterservice.controller;

import lombok.RequiredArgsConstructor;
import org.authservice.Response.Response;
import org.authservice.Response.ResponseStatus;
import org.common.models.User;
import org.posterservice.config.mapper.FriendShipMapper;
import org.posterservice.services.FriendRequestService;
import org.posterservice.services.FriendShipService;
import org.posterservice.services.UserFriendsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;

    private final FriendRequestService friendRequestService;
    private final UserFriendsService userFriendsService;
    private final FriendShipMapper friendShipMapper;

    @PostMapping("/request/{receiver}")
    public ResponseEntity<Response> request(@AuthenticationPrincipal User sender, @PathVariable String receiver) {
         try {
            friendShipService.sendFriendRequest(sender, receiver);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, receiver));
        }catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @PutMapping("/accept/{sender}")
    public ResponseEntity<Response> accept(@AuthenticationPrincipal User accepter, @PathVariable String sender) {
        try {
            friendShipService.acceptFriendRequest(accepter, sender);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, sender));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @PutMapping("/decline/{sender}")
    public ResponseEntity<Response> decline(@AuthenticationPrincipal User decliner, @PathVariable String sender) {
        try {
            friendShipService.declineFriendRequest(decliner, sender);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, sender));
        }catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @GetMapping("/friends")
    public ResponseEntity<Response> friends(@AuthenticationPrincipal User user,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int siz) {
        try {
            List<User> friends = userFriendsService.searchFriendsByUser(user, PageRequest.of(page, siz));
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, friendShipMapper.toUserDTOList(friends)));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @GetMapping("/requests")
    public ResponseEntity<Response> requests(@AuthenticationPrincipal User user,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int siz) {
        //TODO
        return null;
    }



    @DeleteMapping("/delete/{friend}")
    public ResponseEntity<Response> friendDelete(@AuthenticationPrincipal User user, @PathVariable String friend ) {
        try {
            userFriendsService.deleteFriend(user, friend);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }


}
