package com.mehmetsolak.mini_chat_app.user.application;

import com.mehmetsolak.mini_chat_app.blob.api.contracts.BlobContract;
import com.mehmetsolak.mini_chat_app.blob.api.contracts.dto.BlobContractResponse;
import com.mehmetsolak.mini_chat_app.user.domain.exceptions.UserNotFoundException;
import com.mehmetsolak.mini_chat_app.user.domain.models.User;
import com.mehmetsolak.mini_chat_app.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAvatarService {

    private final BlobContract blobContract;
    private final UserRepository userRepository;

    @Transactional
    public void upload(MultipartFile avatar, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        String oldAvatarPublicId = user.getAvatarPublicId();
        BlobContractResponse uploaded = blobContract.uploadAvatar(avatar);
        user.setAvatarUrl(uploaded.url());
        user.setAvatarPublicId(uploaded.publicId());
        userRepository.save(user);
        if(oldAvatarPublicId != null) {
            blobContract.delete(oldAvatarPublicId);
        }
    }
}
