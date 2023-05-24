package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.EmailModel;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.repositories.EmailRepository;
import dev.fernando.agileblog.repositories.PostRepository;
import dev.fernando.agileblog.services.EmailService;
import dev.fernando.agileblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImp implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    EmailService emailService;


    @Override
    public PostModel save(PostModel postModel) {
        return postRepository.save(postModel);
    }

    @Override
    public void delete(PostModel postModel) {
        postRepository.delete(postModel);
    }

    @Override
    public Page<PostModel> findAll(Specification<PostModel> spec, Pageable pageable) {
        return postRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<PostModel> findById(UUID postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void incrementViews(UUID postId) {
        Optional<PostModel> postModelOptional = postRepository.findById(postId);
        if (postModelOptional.isPresent()) {
            PostModel post = postModelOptional.get();
            post.setViews(post.getViews() + 1);
            postRepository.save(post);
        }
    }
    @Override
    public List<PostModel> searchPosts(String searchTerm) {
        List<PostModel> postModels = postRepository.findByTagsContainingIgnoreCase(searchTerm);
        List<PostModel> postDtos = new ArrayList<>();
        for (PostModel postModel : postModels) {
            postDtos.add(postModel);
        }
        return postDtos;
    }

    @Override
    public void sendNewPostNotification(PostModel postModel) {
        List<EmailModel> emails = emailRepository.findUsersWithActiveNewsletter();

        for (EmailModel email : emails) {

            if (emailRepository.existsByEmailTo(email.getEmailTo())) {
                continue;
            }
            EmailModel emailModel = new EmailModel();
            emailModel.setEmailFrom("Blog Agil" + " <metodologia-agil@devluisoliveira.com.br>");
            emailModel.setEmailTo(email.getEmailTo());
            emailModel.setSubject("Novo post no blog falando sobre: " + postModel.getTitle());
            emailModel.setName(email.getName());
            emailModel.setText("Olá " + email.getName() + ", adicionamos um novo post com o tema '" + postModel.getTitle() + "'. Clique aqui para conferir: " + "https://metodologia-agil.com.br/post/" + postModel.getPostId());
            emailModel.setActiveNewsletter(true);
            emailService.sendEmail(emailModel);
        }
    }
}
