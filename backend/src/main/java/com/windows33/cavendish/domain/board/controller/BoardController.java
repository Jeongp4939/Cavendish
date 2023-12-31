package com.windows33.cavendish.domain.board.controller;

import com.windows33.cavendish.domain.board.dto.request.BoardAddRequestDto;
import com.windows33.cavendish.domain.board.dto.request.BoardModifyRequestDto;
import com.windows33.cavendish.domain.board.dto.response.BoardDetailResponseDto;
import com.windows33.cavendish.domain.board.dto.response.BoardListResponseDto;
import com.windows33.cavendish.domain.board.dto.response.BoardModifyFormResponseDto;
import com.windows33.cavendish.domain.board.service.BoardQueryService;
import com.windows33.cavendish.domain.board.service.BoardService;
import com.windows33.cavendish.global.jwt.UserPrincipal;
import com.windows33.cavendish.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Board", description = "게시판 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardQueryService boardQueryService;

    @Operation(summary = "게시글 작성", description = "게시글 작성")
    @Parameters({
            @Parameter(name = "memberLoginRequestDto", description = "게시글 정보"),
            @Parameter(name = "multipartFiles", description = "이미지")
    })
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResponse<Integer> articleAdd(
            @RequestPart(value = "data") BoardAddRequestDto boardAddRequestDto,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return CommonResponse.OK(boardService.addArticle(boardAddRequestDto, multipartFiles, userPrincipal.getId()));
    }

    @Operation(summary = "글 목록 조회", description = "글 목록 조회")
    @Parameters({
            @Parameter(name = "pageable", description = "페이지 정보"),
            @Parameter(name = "type", description = "검색 타입")
    })
    @GetMapping
    public CommonResponse<Page<BoardListResponseDto>> articleList(
            @PageableDefault(sort="createDateTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String type,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return CommonResponse.OK(boardQueryService.findBoardList(pageable, type, userPrincipal!=null?userPrincipal.getId():null));
    }

    @Operation(summary = "글 상세 조회", description = "글 상세 조회")
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 ID")
    })
    @GetMapping("/detail/{boardId}")
    public CommonResponse<BoardDetailResponseDto> articleDetails(
            @PathVariable("boardId") Integer boardId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.increaseViewCount(boardId, userPrincipal!=null?userPrincipal.getId():null);

        return CommonResponse.OK(boardQueryService.findBoardDetail(boardId, userPrincipal!=null?userPrincipal.getId():null));
    }

    @Operation(summary = "글 삭제", description = "글 삭제")
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 ID")
    })
    @DeleteMapping("/delete/{boardId}")
    public CommonResponse<Void> articleRemove(
            @PathVariable("boardId") Integer boardId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        boardService.removeArticle(boardId, userPrincipal.getId());

        return CommonResponse.OK(null);
    }

    @Operation(summary = "글 수정 Interface", description = "글 수정 Interface")
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 ID")
    })
    @GetMapping("/update/{boardId}")
    public CommonResponse<BoardModifyFormResponseDto> articleModify(
            @PathVariable("boardId") Integer boardId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return CommonResponse.OK( boardQueryService.findBoardUpdateForm(boardId, userPrincipal.getId()));
    }

    @Operation(summary = "글 수정", description = "글 수정")
    @Parameters({
            @Parameter(name = "boardId", description = "게시글 ID")
    })
    @PutMapping
    public CommonResponse<Integer> articleModify(
            @RequestPart(value = "data") BoardModifyRequestDto boardModifyRequestDto,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return CommonResponse.OK(boardService.modifyArticle(boardModifyRequestDto, multipartFiles, userPrincipal.getId()));
    }

    @Operation(summary = "좋아요", description = "좋아요")
    @PutMapping("/detail/{boardId}/like")
    public CommonResponse<Boolean> articleLike(
            @PathVariable("boardId") Integer boardId,
            @Parameter(hidden = true) @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return CommonResponse.OK(boardService.doLike(boardId, userPrincipal.getId()));
    }

}