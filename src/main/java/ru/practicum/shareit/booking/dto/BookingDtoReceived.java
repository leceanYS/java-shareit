package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.marker.Marker;
import ru.practicum.shareit.valid.StartBeforeEndDateValid;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@StartBeforeEndDateValid
public class BookingDtoReceived {

    @NotNull(groups = {Marker.Create.class})
    @FutureOrPresent(groups = {Marker.Create.class})
    private LocalDateTime start;

    private LocalDateTime end;

    @NotNull(groups = {Marker.Create.class})
    private Long itemId;
}