package com.mehmetsolak.mini_chat_app.user.application;

import com.mehmetsolak.mini_chat_app.blob.api.contracts.BlobContract;
import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import com.mehmetsolak.mini_chat_app.user.domain.exceptions.UserNotFoundException;
import com.mehmetsolak.mini_chat_app.user.domain.models.User;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserAvatarService Unit Tests")
public class UserAvatarServiceTest {

    @Mock
    private BlobContract blobContract;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultipartFile avatar;

    @InjectMocks
    private UserAvatarService userAvatarService;

    @Test
    @DisplayName("It must throw UserNotFoundException and not call BlobContract when user does not exist")
    public void shouldThrowException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userAvatarService.upload(avatar, userId));

        verify(blobContract, never()).uploadAvatar(any());
        verify(blobContract, never()).delete(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("It must upload new avatar, update user, and delete old avatar when user has an existing avatar")
    public void shouldUploadAvatarAndDeleteOldOne_whenUserHasExistingAvatar() {
        UUID userId = UUID.randomUUID();
        String oldAvatarPublicId = "old-public-id";
        String newUrl = "https://blob.example.com/new-avatar.png";
        String newPublicId = "new-public-id";

        User user = User.builder()
                .id(userId)
                .avatarPublicId(oldAvatarPublicId)
                .build();

        BlobContractResponse response = new BlobContractResponse(newUrl, newPublicId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(blobContract.uploadAvatar(avatar)).thenReturn(response);

        userAvatarService.upload(avatar, userId);

        assertEquals(newUrl, user.getAvatarUrl());
        assertEquals(newPublicId, user.getAvatarPublicId());

        verify(userRepository).save(user);
        verify(blobContract).delete(oldAvatarPublicId);
    }

    @Test
    @DisplayName("It must upload new avatar and not call delete when user has no existing avatar")
    public void shouldUploadAvatarWithoutDeleting_whenUserHasNoExistingAvatar() {
        UUID userId = UUID.randomUUID();
        String newUrl = "https://blob.example.com/new-avatar.png";
        String newPublicId = "new-public-id";

        User user = User.builder()
                .id(userId)
                .avatarPublicId(null)
                .build();

        BlobContractResponse response = new BlobContractResponse(newUrl, newPublicId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(blobContract.uploadAvatar(avatar)).thenReturn(response);

        userAvatarService.upload(avatar, userId);

        assertEquals(newUrl, user.getAvatarUrl());
        assertEquals(newPublicId, user.getAvatarPublicId());

        verify(userRepository).save(user);
        verify(blobContract, never()).delete(any());
    }
}
