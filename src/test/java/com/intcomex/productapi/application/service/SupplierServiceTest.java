package com.intcomex.productapi.application.service;

import com.intcomex.productapi.domain.model.Supplier;
import com.intcomex.productapi.domain.repository.SupplierRepository;
import com.intcomex.productapi.web.dto.SupplierRequestDto;
import com.intcomex.productapi.web.dto.SupplierResponseDto;
import com.intcomex.productapi.web.mapper.SupplierMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SupplierServiceTest {

    @Mock
    private SupplierRepository repository;

    @Mock
    private SupplierMapper mapper;

    @InjectMocks
    private SupplierService service;

    public SupplierServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Supplier> suppliers = List.of(new Supplier());
        when(repository.findAll()).thenReturn(suppliers);
        when(mapper.toDto(any())).thenReturn(new SupplierResponseDto());

        List<SupplierResponseDto> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSave() {
        SupplierRequestDto dto = new SupplierRequestDto();
        Supplier supplier = new Supplier();
        when(mapper.toEntity(dto)).thenReturn(supplier);
        when(repository.save(supplier)).thenReturn(supplier);
        when(mapper.toDto(supplier)).thenReturn(new SupplierResponseDto());

        SupplierResponseDto result = service.save(dto);

        assertNotNull(result);
        verify(repository).save(supplier);
    }
}
