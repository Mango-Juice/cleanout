import React from 'react';
import styled from 'styled-components/native';
import { ScrollView } from 'react-native';
import colors from '../../styles/colors';
import splash from '../../../assets/splash.png';

/**
 * 좌우 스크롤 가능한 이미지 리스트를 보여주는 컴포넌트
 * @param {Array} sources - 이미지 Uri 배열
 * @param {string?} width - 이미지 너비 (기본: 200px)
 * @param {string?} height - 이미지 높이 (기본: 200px)
 * @param {string?} imageSpaceWidth - 이미지가 차지하는 너비 (기본: 110px)
 */
export default ScrollImageList = (props) => {
    const { sources, width = '150px', height = '150px', imageSpaceWidth = '160px', ...others } = props;

    return sources.length > 0 && sources[0].length > 0 ? (
        <ScrollView
            contentContainerStyle={{ paddingTop: '3%', paddingBottom: '3%', flexGrow: 1, justifyContent: 'center' }}
            horizontal={true}
            {...others}
        >
            {sources.map((source, index) => (
                <ImageContainer key={index} width={imageSpaceWidth}>
                    <StyledImage source={{ uri: source }} width={width} height={height}></StyledImage>
                </ImageContainer>
            ))}
        </ScrollView>
    ) : (
        <PlaceHolder height={height} marginVertical='3%'>
            <PlaceHolderText>여기에 불러온 이미지가 표시됩니다.</PlaceHolderText>
        </PlaceHolder>
    );
};

const StyledImage = styled.Image`
    width: ${(props) => props.width};
    height: ${(props) => props.height};
    object-fit: cover;
`;

const ImageContainer = styled.View`
    width: ${(props) => props.width};
    justify-content: center;
    align-items: center;
`;

const PlaceHolder = styled.View`
    width: 100%;
    height: ${(props) => props.height};
    background-color: ${colors.placeholder};
    margin: ${(props) => props.marginVertical} 0;
    align-items: center;
    justify-content: center;
`;

const PlaceHolderText = styled.Text`
    color: ${colors.disabled};
    font-size: 13px;
`;
