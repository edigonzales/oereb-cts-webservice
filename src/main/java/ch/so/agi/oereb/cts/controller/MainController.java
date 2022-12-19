package ch.so.agi.oereb.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.so.agi.oereb.cts.dto.ProbeResultDTO;
import ch.so.agi.oereb.cts.entity.ProbeResult;
import ch.so.agi.oereb.cts.repository.ProbeResultRepository;
import ch.so.agi.oereb.cts.service.OerebValidatorService;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
//@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProbeResultRepository probeResultRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/")
    public List<ProbeResultDTO> show(/*Model model*/) {        
        List<ProbeResult> probeResults = probeResultRepository.findAll();

        // Weil LAZY werden mit findAll zuerst nur die ProbeResults requestet.
        // Erst beim Mappen werden auch die jeweiligen CheckResults abgefragt.
        // Diese will ich mit in der Übersicht gar nicht. Entweder beim 
        // ModelMapper abstreifen oder eine eigene Query im Respository,
        // was sich aber es garstig herausstellt: Was funktioniert hat, ist
        // wenn man alle Attribute ausschreibt als native Query.
        modelMapper.typeMap(ProbeResult.class, ProbeResultDTO.class).addMappings(mapper -> mapper.skip(ProbeResultDTO::setCheckResults));
        
        List<ProbeResultDTO> probeResultsDTO = probeResults
                .stream()
                .map(probeResult -> modelMapper.map(probeResult, ProbeResultDTO.class))
                .collect(Collectors.toList());

        

        // Eventuell doch Repository:
        // - Übersicht für Titelseite
        // - Detail pro Requestgruppe
        
        //model.addAttribute("repositories", iliRepoList);
//        return "gui";
        return probeResultsDTO;
    } 

}
