package org.posterservice.controller;

import lombok.RequiredArgsConstructor;
import org.common.model.User;
import org.common.user.details.UserDetailsImpl;
import org.posterservice.config.application.FriendShipMapper;
import org.posterservice.dto.http.FriendRequestDTO;
import org.posterservice.response.Response;
import org.posterservice.response.ResponseStatus;
import org.posterservice.services.FriendRequestService;
import org.posterservice.services.FriendShipService;
import org.posterservice.services.FriendsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequestMapping("/friendship")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;
    private final FriendRequestService friendRequestService;
    private final FriendsService friendsService;

    private final FriendShipMapper friendShipMapper;

    @PostMapping("/request/{receiver}")
    public ResponseEntity<Response> request(@AuthenticationPrincipal UserDetails sender, @PathVariable String receiver) {
         try {
            friendShipService.sendFriendRequest(sender, receiver);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, receiver));
        }catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @PutMapping("/accept/{sender}")
    public ResponseEntity<Response> accept(@AuthenticationPrincipal UserDetails accepter, @PathVariable String sender) {
        try {
            friendShipService.acceptFriendRequest(accepter, sender);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, sender));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @PutMapping("/decline/{sender}")
    public ResponseEntity<Response> decline(@AuthenticationPrincipal UserDetails decliner, @PathVariable String sender) {
        try {
            friendShipService.declineFriendRequest(decliner, sender);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, sender));
        }catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @GetMapping("/friends")
    public ResponseEntity<Response> friends(@AuthenticationPrincipal UserDetails user,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int siz) {
        try {
            List<User> friends = friendsService.searchFriendsByUser(user, PageRequest.of(page, siz));
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, friendShipMapper.toUserDTOList(friends)));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @GetMapping("/requests")
    public ResponseEntity<Response> requests(@AuthenticationPrincipal UserDetails user,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int siz) {
        try {
            List<FriendRequestDTO> requests = friendRequestService.getFriendRequestsByUser(((UserDetailsImpl) user).getUser(), PageRequest.of(page, siz));
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, requests));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }



    @DeleteMapping("/delete/{friend}")
    public ResponseEntity<Response> friendDelete(@AuthenticationPrincipal UserDetails user, @PathVariable String friend ) {
        try {
            friendsService.deleteFriend(user, friend);
            return ResponseEntity.ok(new Response(ResponseStatus.VALIDATE, null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new Response(ResponseStatus.FAILURE, e.getMessage()));
        }
    }


}
