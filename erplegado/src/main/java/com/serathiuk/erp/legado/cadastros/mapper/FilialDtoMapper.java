package com.serathiuk.erp.legado.cadastros.mapper;

import com.serathiuk.erp.legado.cadastros.dto.FilialDto;
import com.serathiuk.erp.legado.cadastros.model.Filial;

public class FilialDtoMapper {

    public static FilialDto toDto(Filial Filial) {
        if(Filial == null) return null;

        var FilialDto = new FilialDto();
        FilialDto.setId(Filial.getId());
        FilialDto.setNome(Filial.getNome());
        return FilialDto;
    }

    public static Filial toFilial(FilialDto FilialDto) {
        if(FilialDto == null) return null;

        var Filial = new Filial();
        Filial.setId(FilialDto.getId());
        Filial.setNome(FilialDto.getNome());
        return Filial;
    }

}
