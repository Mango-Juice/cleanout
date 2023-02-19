import React from 'react';
import colors from '../../styles/colors';
import styled from 'styled-components/native';

/**
 * 기본 텍스트 입력 atom
 * 필요한 경우 props 추가해서 사용
 * @param {string} placeholder 텍스트 입력창에 표시될 텍스트
 * @param {string?} value 텍스트 입력창의 초기값
 * @param {boolean?} isPassword 텍스트 입력창의 값이 비밀번호인지 여부(기본: false)
 * @param {boolean?} disabled 텍스트 입력창의 활성화 여부(기본: false)
 * @param {string?} fontSize 텍스트 입력창의 폰트 크기(기본: 15px)
 * @param {function?} onTextChangeListener 텍스트 입력창의 값이 변경될 때 실행할 함수((string) => void)
 */
export default TextInput = (props) => {
    const {
        placeholder,
        value = '',
        isPassword = false,
        disabled = false,
        autoCorrect = false,
        fontSize = '15px',
        onTextChangeListener = (_) => {},
        ...others
    } = props;

    const onTextChange = (text) => {
        onTextChangeListener(text);
    };

    return (
        <TextInputContainer {...others}>
            <StyledTextInput
                fontSize={fontSize}
                defaultValue={value}
                secureTextEntry={isPassword}
                editable={!disabled}
                placeholder={placeholder}
                placeholderTextColor={colors.disabled}
                onChangeText={onTextChange}
            ></StyledTextInput>
        </TextInputContainer>
    );
};

const TextInputContainer = styled.View`
    align: left;
`;

const StyledTextInput = styled.TextInput`
    padding: 5px;
    border-bottom-width: 1px;
    font-size: ${(props) => props.fontSize};
    border-color: ${colors.border};
`;
