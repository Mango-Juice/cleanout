package com.backend.waste.controller;

import com.backend.member.domain.Member;
import com.backend.util.ControllerTest;
import com.backend.waste.domain.Waste;
import com.backend.waste.dto.request.PatchWaste;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class WasteControllerTest extends ControllerTest {

//    @Test
//    @DisplayName("이미지를 업로드하면 폐기물 등록에 성공합니다")
//    void createWaste200() throws Exception {
//        //given
//        MultipartFile imageFile1 = new MockMultipartFile("image1", "waste1.PNG", MediaType.IMAGE_PNG_VALUE, "<<wasteImage1>>".getBytes());
//        MultipartFile imageFile2 = new MockMultipartFile("image2", "waste2.PNG", MediaType.IMAGE_PNG_VALUE, "<<wasteImage2>>".getBytes());
//        MultipartFile imageFile3 = new MockMultipartFile("image3", "waste3.PNG", MediaType.IMAGE_PNG_VALUE, "<<wasteImage3>>".getBytes());
//        Member member = saveMemberInRepository();
//        MockHttpSession session = loginMemberSession(member);
//
//        //
//        mockMvc.perform(
//                        multipart("/waste-management/image")
//                                .file("image", imageFile1.getBytes())
//                                .file("image", imageFile2.getBytes())
//                                .file("image", imageFile3.getBytes())
//                                .session(session)
//                                .param("unique","qwerasdfzxcv")
//                                .contentType(MediaType.MULTIPART_FORM_DATA))
//                .andExpect(status().isOk())
//                .andDo(document("waste/create/200"));
//    }
//
    @Test
    @DisplayName("등록된 폐기물일 경우 가격이 책정됩니다.")
    void updateWaste200() throws Exception {
        //given
        Member member = saveMemberInRepository();
        MockHttpSession session = loginMemberSession(member);
        String unique = "qwerasdfzxcv";
        saveWaste(member, unique);

        PatchWaste patchWaste = PatchWaste.builder()
                .wasteName("냉장고-300L이상")
                .price(6000)
                .unique(unique)
                .build();

        String patchWasteJson = objectMapper.writeValueAsString(patchWaste);

        //
        mockMvc.perform(
                patch("/waste-management/waste")
                        .session(session)
                        .contentType(APPLICATION_JSON)
                        .content(patchWasteJson))
                .andExpect(status().isOk())
                .andDo(document("waste/update/200",
                        requestFields(
                                fieldWithPath("wasteName").description("제품명"),
                                fieldWithPath("price").description("가격"),
                                fieldWithPath("unique").description("고유문자열 이름"))
                        ));
    }

    @Test
    @DisplayName("등록되지 않은 폐기물일 경우 가격이 책정되지 않습니다")
    void updateWaste400() throws  Exception {
        Member member = saveMemberInRepository();
        MockHttpSession session = loginMemberSession(member);
        String unique = "qwerasdfzxcv";

        PatchWaste patchWaste = PatchWaste.builder()
                .wasteName("냉장고-300L이상")
                .price(6000)
                .unique(unique)
                .build();

        String patchWasteJson = objectMapper.writeValueAsString(patchWaste);

        mockMvc.perform(
                        patch("/waste-management/waste")
                                .session(session)
                                .contentType(APPLICATION_JSON)
                                .content(patchWasteJson))
                .andExpect(status().isNotFound())
                .andDo(document("waste/update/400",
                        requestFields(
                                fieldWithPath("wasteName").description("제품명"),
                                fieldWithPath("price").description("가격"),
                                fieldWithPath("unique").description("고유문자열 이름"))
                ));
    }
}
