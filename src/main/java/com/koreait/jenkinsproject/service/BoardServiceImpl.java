package com.koreait.jenkinsproject.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.jenkinsproject.domain.Board;
import com.koreait.jenkinsproject.domain.BoardAttach;
import com.koreait.jenkinsproject.repository.BoardRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardRepository repository;
	
	public BoardServiceImpl(SqlSessionTemplate sqlSession) {
		repository = sqlSession.getMapper(BoardRepository.class);
	}
	
	@Override
	public Map<String, Object> addBoard(MultipartHttpServletRequest multipartRequest) {
		
		String writer = multipartRequest.getParameter("writer");
		String title = multipartRequest.getParameter("title");
		String content = multipartRequest.getParameter("content");
		Optional<String> opt = Optional.ofNullable(multipartRequest.getHeader("X-Forwarded-For"));
		String ip = opt.orElse(multipartRequest.getRemoteAddr());
		
		Board board = Board.builder()
				.writer(writer)
				.title(title)
				.content(content)
				.ip(ip)
				.build();	
		
		System.out.println("DB수행전:" + board);
		int result = repository.insertBoard(board);
		System.out.println("DB수행후:" + board);
		return null;
	}

}
