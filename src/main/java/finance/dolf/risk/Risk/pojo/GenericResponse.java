package finance.dolf.risk.Risk.pojo;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import finance.dolf.risk.Risk.constants.ResponseMessages;
import lombok.*;


@JsonInclude(Include.NON_EMPTY)
@Value
@Builder
public class GenericResponse<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1008737297980098943L;

	private final Long              totalCount;
    private final String            statusCode;


    @JsonInclude(Include.ALWAYS)
    private final T                 data;

    private final APIError          error;
    @ToString(includeFieldNames=true)
    @Data
    @AllArgsConstructor
    public static class APIError implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        @NonNull
        private String            msg              = ResponseMessages.SOME_ERROR_OCCURRED;

    }
}