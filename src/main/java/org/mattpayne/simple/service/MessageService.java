package org.mattpayne.simple.service;

import java.util.List;
import java.util.stream.Collectors;
import org.mattpayne.simple.domain.Message;
import org.mattpayne.simple.model.MessageDTO;
import org.mattpayne.simple.repos.MessageRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageDTO> findAll() {
        return messageRepository.findAll(Sort.by("id"))
                .stream()
                .map(message -> mapToDTO(message, new MessageDTO()))
                .collect(Collectors.toList());
    }

    public MessageDTO get(final Long id) {
        return messageRepository.findById(id)
                .map(message -> mapToDTO(message, new MessageDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final MessageDTO messageDTO) {
        final Message message = new Message();
        mapToEntity(messageDTO, message);
        return messageRepository.save(message).getId();
    }

    public void update(final Long id, final MessageDTO messageDTO) {
        final Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(messageDTO, message);
        messageRepository.save(message);
    }

    public void delete(final Long id) {
        messageRepository.deleteById(id);
    }

    private MessageDTO mapToDTO(final Message message, final MessageDTO messageDTO) {
        messageDTO.setId(message.getId());
        messageDTO.setBody(message.getBody());
        return messageDTO;
    }

    private Message mapToEntity(final MessageDTO messageDTO, final Message message) {
        message.setBody(messageDTO.getBody());
        return message;
    }

}
