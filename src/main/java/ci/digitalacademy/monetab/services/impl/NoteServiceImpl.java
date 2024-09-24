package ci.digitalacademy.monetab.services.impl;

import ci.digitalacademy.monetab.repositories.NoteRepository;
import ci.digitalacademy.monetab.services.NoteService;
import ci.digitalacademy.monetab.services.dto.NoteDTO;
import ci.digitalacademy.monetab.services.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper noteMapper;


    @Override
    public NoteDTO save(NoteDTO noteDTO) {
        return null;
    }

    @Override
    public NoteDTO update(NoteDTO noteDTO) {
        return null;
    }

    @Override
    public Optional<NoteDTO> findOne(Long id) {
        return noteRepository.findById(id).map(note -> {
            return noteMapper.toDto(note);
        });
    }

    @Override
    public List<NoteDTO> findAll() {
        return List.of();
    }

    @Override
    public void delecte(Long id) {

    }
}
