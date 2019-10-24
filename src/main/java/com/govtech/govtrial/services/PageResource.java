package com.govtech.govtrial.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageResource<T> extends RepresentationModel implements Page<T> {
	
	private final Page<T> page;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public PageResource(
			Page<T> page, 
			String pageParam,
			String sizeParam,
			String minimumSalaryParam,
			Double minimumSalary,
			String maximumSalaryParam,
			Double maximumSalary) {
		super();
		this.page = page;
		
		if(page.hasPrevious()) {
			Link link = buildPageLink(
					pageParam,
					page.getNumber()-1,
					sizeParam,
					page.getSize(),
					minimumSalaryParam, 
					minimumSalary,
					maximumSalaryParam, 
					maximumSalary,
					IanaLinkRelations.PREV
			);
			add(link);
		}
		if(page.hasNext()) {
			Link link = buildPageLink(
					pageParam,
					page.getNumber()+1,
					sizeParam,
					page.getSize(),
					minimumSalaryParam, 
					minimumSalary,
					maximumSalaryParam, 
					maximumSalary,
					IanaLinkRelations.NEXT
			);
			add(link);
		}
		
		Link link = buildPageLink(
				pageParam,
				0,
				sizeParam,
				page.getSize(),
				minimumSalaryParam, 
				minimumSalary,
				maximumSalaryParam, 
				maximumSalary,
				IanaLinkRelations.FIRST
		);
		add(link);
		
		int indexOfLastPage = page.getTotalPages() - 1;
		link = buildPageLink(
				pageParam,
				indexOfLastPage,
				sizeParam,
				page.getSize(),
				minimumSalaryParam, 
				minimumSalary,
				maximumSalaryParam, 
				maximumSalary,
				IanaLinkRelations.LAST
			);
		add(link);
		
		link = buildPageLink(
				pageParam,
				page.getNumber(),
				sizeParam,
				page.getSize(),
				minimumSalaryParam, 
				minimumSalary,
				maximumSalaryParam, 
				maximumSalary,
				IanaLinkRelations.SELF)
			;
		add(link);
	}
	
	private ServletUriComponentsBuilder createBuilder() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri();
	}
	
	private Link buildPageLink(
			String pageParam,
			int page,
			String sizeParam,
			int size,
			String minimumSalaryParam,
			Double minimumSalary,
			String maximumSalaryParam,
			Double maximumSalary,
			LinkRelation linkRelation
		) {
		UriComponentsBuilder path = createBuilder()
				.queryParam(pageParam,page)
				.queryParam(sizeParam,size);
		
		if( minimumSalary != null ) {
			path.queryParam(minimumSalaryParam, minimumSalary);
		}
		if( maximumSalary != null ) {
			path.queryParam(maximumSalaryParam, maximumSalary);
		}
		
		String linkPath = path.build()
							.toUriString();
		Link link = new Link(linkPath,linkRelation);
		return link;
	}
	
	@Override
	public int getNumber() {
		return page.getNumber();
	}

	@Override
	public int getSize() {
		return page.getSize();
	}

	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	@Override
	public long getTotalElements() {
		return page.getTotalElements();
	}

	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

	@Override
	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	public boolean hasContent() {
		return page.hasContent();
	}

	@Override
	public Sort getSort() {
		return page.getSort();
	}

	@Override
	public boolean isFirst() {
		return page.isFirst();
	}

	@Override
	public boolean isLast() {
		return page.isLast();
	}

	@Override
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	@Override
	public Pageable nextPageable() {
		return page.nextPageable();
	}

	@Override
	public Pageable previousPageable() {
		return page.previousPageable();
	}

	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> converter) {
		// TODO Auto-generated method stub
		return null;
	}
}