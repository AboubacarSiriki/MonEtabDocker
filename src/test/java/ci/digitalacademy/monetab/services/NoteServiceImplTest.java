package ci.digitalacademy.monetab.services;

import ci.digitalacademy.monetab.models.Note;
import ci.digitalacademy.monetab.repositories.NoteRepository;
import ci.digitalacademy.monetab.services.dto.NoteDTO;
import ci.digitalacademy.monetab.services.impl.NoteServiceImpl;
import ci.digitalacademy.monetab.services.mapper.NoteMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private  NoteMapper noteMapper;

    @InjectMocks
    private NoteServiceImpl noteService;

    @Test
    public void WhenFindOne_thenReturnNote(){

        when(noteRepository.findById(50L)).thenReturn(Optional.of(new Note(50L,"12","Math")));
        Optional<NoteDTO> note = noteService.findOne(50L);
          Assertions.assertNotNull(note.orElse(null));

    }

}
