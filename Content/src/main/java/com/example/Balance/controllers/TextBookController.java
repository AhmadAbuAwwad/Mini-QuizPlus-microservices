package com.example.Balance.controllers;

import com.example.Balance.controllers.requests.TextBookRequest;
import com.example.Balance.dto.TextBookDTO;
import com.example.Balance.service.TextBookService;
import com.example.Balance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/textbook")
public class TextBookController {

    @Autowired
    TextBookService textBookService;

    @Autowired
    Mapper mapper;

    /**
     * @param id
     * @return
     */
    @GetMapping("/getTextBookById/{id}")
    public ResponseEntity<TextBookDTO> getTextBookById(@PathVariable long id) {
        return ResponseEntity.ok(mapper.map(TextBookDTO.class, textBookService.getTextBookById(id)));
    }

    /**
     * @param textBookRequest
     * @return
     */
    @PostMapping("/createTextBook")
    public ResponseEntity<TextBookDTO> createTextBook(@RequestBody TextBookRequest textBookRequest) {
        return ResponseEntity.ok(mapper.map(TextBookDTO.class, textBookService.createTextBook(textBookRequest)));
    }

    /**
     * @param id
     * @param textBookRequest
     * @return
     */
    @PutMapping("/updateTextBook/{id}")
    public ResponseEntity<TextBookDTO> updateTextBook(@PathVariable long id, @RequestBody TextBookRequest textBookRequest) {
        return ResponseEntity.ok(mapper.map(TextBookDTO.class, textBookService.updateTextBook(id, textBookRequest)));
    }

    /**
     * @return
     */
    @GetMapping("/getAllTextBooks")
    public ResponseEntity<List<TextBookDTO>> getAllTextBooks() {
        List<TextBookDTO> textBookDTOS = new ArrayList<>();
        textBookService.getAllBooks().stream()
                .filter(book -> textBookDTOS.add(mapper.map(TextBookDTO.class, book)));
        return ResponseEntity.ok(textBookDTOS);
    }
}
