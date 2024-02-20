package ru.practicum.shareit.item.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.validationGroups.ItemOnCreate;
import ru.practicum.shareit.item.model.validationGroups.ItemOnUpdate;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor


public class ItemController {
    private static final String HEADER_USER_ID = "X-Sharer-User-Id";
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("/items")
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto itemDto, Long userId) {
        // Проверяем существование пользователя перед созданием предмета
        if (!userService.userExists(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Создаем предмет
        ItemDto createdItem = itemService.create(itemDto, userId);

        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }




    @PatchMapping("/{itemId}")
    @Operation(summary = "Обновление вещи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item update"),
            @ApiResponse(responseCode = "403", description = "Insufficient rights"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ItemDto update(@RequestHeader(HEADER_USER_ID) long userId,
                          @Validated(ItemOnUpdate.class) @RequestBody ItemDto item,
                          @PathVariable long itemId) {
        log.info("Получен запрос на обновление вещи с id {} от пользователя {}", itemId, userId);
        return itemService.update(item, userId, itemId);
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item get"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    public ItemDto get(@PathVariable long itemId) {
        log.info("Получен запрос на получение вещи с id {}", itemId);
        return itemService.get(itemId);
    }

    @GetMapping
    @Operation(summary = "Получение всех вещей пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items get"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public List<ItemDto> getAll(@RequestHeader(HEADER_USER_ID) Long userId) {
        log.info("Получен запрос на получение всех вещей пользователя {}", userId);
        return itemService.getAllByOwner(userId);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск по вещам")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found")
    })
    public List<ItemDto> search(@RequestParam String text) {
        if (text.isBlank()) {
            log.info("Получен запрос на поиск с пустым значением в запросе");
            return List.of();
        } else {
            log.info("Получен запрос на поиск с текстом {}", text);
            return itemService.search(text);
        }
    }
}