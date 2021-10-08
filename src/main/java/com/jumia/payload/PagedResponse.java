package com.jumia.payload;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagedResponse<T> {
	private List<T> data;
	private Map<String, Object> metaData;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public PagedResponse(List<T> data, int page, int size, long totalElements, int totalPages, boolean isLast) {
        this.data = data;
        this.metaData = new LinkedHashMap();
        this.metaData.put("page", Long.valueOf(page) + 1);
        this.metaData.put("size", Long.valueOf(size));
        this.metaData.put("total-elements", Long.valueOf(totalElements));
        this.metaData.put("total-pages", Long.valueOf(totalPages));
        this.metaData.put("last-page", isLast);
    }
}
