package ma.fstt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ma.fstt.common.messages.BaseResponse;
import ma.fstt.dto.AssistanceDTO;
import ma.fstt.service.AssistanceService;



@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping("/assistances")
public class AssistanceController {

    @Autowired
    private AssistanceService assistanceService;

    @GetMapping
    public ResponseEntity<List<AssistanceDTO>> getAllAssistance() {
        List<AssistanceDTO> list = assistanceService.findAssistanceList();
        return new ResponseEntity<List<AssistanceDTO>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AssistanceDTO>> getAllAssistanceByUserId(@PathVariable Long id) {
        List<AssistanceDTO> list = assistanceService.findAssistanceByUserId(id);
        return new ResponseEntity<List<AssistanceDTO>>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AssistanceDTO> getAssistanceById(@PathVariable Long id) {
        AssistanceDTO list = assistanceService.findByAssistanceId(id);
        return new ResponseEntity<AssistanceDTO>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/name/{id}")
    public ResponseEntity<AssistanceDTO> getAssistanceNameById(@PathVariable Long id) {
        AssistanceDTO list = assistanceService.findByAssistanceId(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = { "/add" })
    public ResponseEntity<BaseResponse> createAssistance(@RequestBody AssistanceDTO userDTO) {
        BaseResponse response = assistanceService.createOrUpdateAssistance(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<BaseResponse> updateAssistance(
            @PathVariable("id") Long id,
            @RequestBody AssistanceDTO updatedAssistanceDTO) {

        BaseResponse response = assistanceService.updateAssistance(id, updatedAssistanceDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


        @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BaseResponse> deleteAssistanceById(@PathVariable Long id) {
        BaseResponse response = assistanceService.deleteAssistance(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

