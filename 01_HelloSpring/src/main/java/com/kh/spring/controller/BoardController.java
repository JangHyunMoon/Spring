package com.kh.spring.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.common.PageBarFactory;
import com.kh.spring.model.vo.Attachment;
import com.kh.spring.model.vo.Board;
import com.kh.spring.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	
	@RequestMapping("/board/boardFormEnd.do")
	public ModelAndView insertBoard(Board board, MultipartFile[] upFile, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		System.out.println(board);
		String saveDir = request.getSession().getServletContext().getRealPath("/resources/upload");
		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		/* 단일파일 */
		/*if (!upFile.isEmpty()) {
			String oriFileName = upFile.getOriginalFilename();
			String ext = oriFileName.substring(oriFileName.indexOf("."));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmmssSSS");
			int rndNum = (int) (Math.random() * 1000);
			String reNamedFile = sdf.format(new Date(System.currentTimeMillis()))
					+ "_" + rndNum + ext;
			try {
				upFile.transferTo(new File(saveDir + "/" + reNamedFile));
			} catch(IOException e) {
				e.printStackTrace();
			}
 		}*/
		List<Attachment> list = new ArrayList();
		for (MultipartFile f : upFile) {
			if (!f.isEmpty()) {
				String OriFileName = f.getOriginalFilename();
				String ext = OriFileName.substring(OriFileName.indexOf("."));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				int rndNum = (int) (Math.random() * 10000);
				String renamedFile = sdf.format(new Date(System.currentTimeMillis())) + "_" + rndNum + ext;
				try {
					f.transferTo(new File(saveDir + "/" + renamedFile));
				} catch(IOException e) {
					e.printStackTrace();
				}
				Attachment a = new Attachment();
				a.setOriginalFilename("OriFileName");
				a.setRenamedFilename(renamedFile);
				list.add(a);
				/*list.add(new Attachment(0, 0, OriFileName, renamedFile,null,null, 0));*/
			}
		}
		
		int result = service.insertBoard(board,list);
		
		mv.setViewName("common/msg");
		return mv;
	}
	
	@RequestMapping("/board/boardForm")
	public String boardForm() {
		return "board/boardForm";
	}
	
	@RequestMapping("/board/boardList")
	public ModelAndView boardList(@RequestParam(value="cPage", required=false, defaultValue="1")int cPage) {
		
		
		int numPerPage = 10;
		ModelAndView mv = new ModelAndView();
		List<Board> list = service.selectList(cPage, numPerPage);
		int totalList = service.selectCount();
		
		mv.addObject("list", list);
		mv.addObject("totalList", totalList);
		mv.addObject("pageBar", PageBarFactory.getPageBar(totalList, cPage, numPerPage, "/spring/board/boardList"));
		mv.setViewName("board/boardList");
		return mv;
	}
	
	/*public String selectBoardList(Model model) {
		List<Board> list = service.selectBoardList();
		System.out.println(list);
		model.addAttribute("list", list);
		return "board/board";
	}*/
}
