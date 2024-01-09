package ma.fstt.service;

import jakarta.transaction.Transactional;
import ma.fstt.common.exceptions.RecordNotFoundException;
import ma.fstt.common.messages.BaseResponse;
import ma.fstt.common.messages.CustomMessage;
import ma.fstt.common.utils.Topic;
import ma.fstt.dto.AssistanceDTO;
import ma.fstt.dto.UserDTO;
import ma.fstt.entity.AssistanceEntity;
import ma.fstt.repo.AssistanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AssistanceService {

    @Autowired
    private AssistanceRepo assistanceRepo;


    private final WebClient webClient;

    public AssistanceService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/v1/auth/users").build();
    }

    public List<AssistanceDTO> findAssistanceList() {
        return assistanceRepo.findAll().stream().map(this::copyAssistanceEntityToDto).collect(Collectors.toList());
    }

    public List<AssistanceDTO> findAssistanceByUserId(Long id) {
        return assistanceRepo.findByUserId(id).stream().map(this::copyAssistanceEntityToDto).collect(Collectors.toList());
    }

    public AssistanceDTO findByAssistanceId(Long assistanceId) {
        AssistanceEntity userEntity = assistanceRepo.findById(assistanceId)
                .orElseThrow(() -> new RecordNotFoundException("Assistance id '" + assistanceId + "' does not exist !"));
        return copyAssistanceEntityToDto(userEntity);
    }

    public BaseResponse createOrUpdateAssistance(AssistanceDTO assistanceDTO) {
        // Vérifie d'abord l'existence de l'utilisateur avant de créer une assistance
        Mono<UserDTO> userMono = webClient.get()
                .uri("/{id}", assistanceDTO.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class);

        // Attendez la réponse du service d'authentification
        UserDTO userResponse = userMono.block();

        if (userResponse != null && userResponse.getId() != null) {
            // L'utilisateur existe, continuez avec la création ou la mise à jour de l'assistance
            AssistanceEntity assistanceEntity = copyAssistanceDtoToEntity(assistanceDTO);
            assistanceRepo.save(assistanceEntity);
            return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
        } else {
            // L'utilisateur n'existe pas, vous pouvez gérer cela en lançant une exception, par exemple
            throw new RecordNotFoundException("L'utilisateur avec l'ID " + assistanceDTO.getUserId() + " n'existe pas.");
        }
    }

    public BaseResponse updateAssistance(Long assistanceId, AssistanceDTO updatedAssistanceDTO) {
        // Check if the assistance with the given ID exists in the database
        if (!assistanceRepo.existsById(assistanceId)) {
            throw new RecordNotFoundException("Assistance id '" + assistanceId + "' does not exist!");
        }

        // Find the existing AssistanceEntity by ID
        AssistanceEntity existingAssistanceEntity = assistanceRepo.findById(assistanceId)
                .orElseThrow(() -> new RecordNotFoundException("Assistance id '" + assistanceId + "' does not exist !"));

        // Update the fields of the existing entity with the values from the updated DTO
        existingAssistanceEntity.setDescription(updatedAssistanceDTO.getDescription());
        existingAssistanceEntity.setDate(updatedAssistanceDTO.getDate());
        existingAssistanceEntity.setStatus(updatedAssistanceDTO.getStatus());

        // Save the updated entity back to the database
        assistanceRepo.save(existingAssistanceEntity);

        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }

    public BaseResponse deleteAssistance(Long assistanceId) {
        if (assistanceRepo.existsById(assistanceId)) {
            assistanceRepo.deleteById(assistanceId);
        } else {
            throw new RecordNotFoundException("No record found for given id: " + assistanceId);
        }
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }


    private AssistanceDTO copyAssistanceEntityToDto(AssistanceEntity assistanceEntity) {
        AssistanceDTO assistanceDTO = new AssistanceDTO();
        assistanceDTO.setAssistanceId(assistanceEntity.getAssitanceId());
        assistanceDTO.setDescription(assistanceEntity.getDescription());
        assistanceDTO.setDate(assistanceEntity.getDate());
        assistanceDTO.setStatus(assistanceEntity.getStatus());
        assistanceDTO.setUserId(assistanceEntity.getUserId());


        return assistanceDTO;
    }

    private AssistanceEntity copyAssistanceDtoToEntity(AssistanceDTO assistanceDTO) {
        AssistanceEntity userEntity = new AssistanceEntity();
        userEntity.setAssitanceId(assistanceDTO.getAssistanceId());
        userEntity.setDescription(assistanceDTO.getDescription());
        userEntity.setDate(assistanceDTO.getDate());
        userEntity.setStatus(assistanceDTO.getStatus());
        userEntity.setUserId(assistanceDTO.getUserId());
        return userEntity;
    }

}

