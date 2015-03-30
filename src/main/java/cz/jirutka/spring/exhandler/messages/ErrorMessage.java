/*
 * Copyright 2014 Jakub Jirutka <jakub@jirutka.cz>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.jirutka.spring.exhandler.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.net.URI;

/**
 * @see <a href="http://tools.ietf.org/html/draft-nottingham-http-problem-06">draft-nottingham-http-problem-06</a>
 */
@Data
@NoArgsConstructor
@ToString(exclude="detail")
@JsonInclude(Include.NON_EMPTY) //for Jackson 2.x
@JsonSerialize(include=Inclusion.NON_EMPTY) //for Jackson 1.x
@XmlRootElement(name="problem") //for JAXB
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * An absolute URI that identifies the problem type. When dereferenced, it
     * SHOULD provide human-readable documentation for the problem type (e.g.,
     * using HTML). When this member is not present, its value is assumed to
     * be "about:blank".
     */
    private URI type;

    /**
     * A short, human-readable summary of the problem type. It SHOULD NOT
     * change from occurrence to occurrence of the problem, except for purposes
     * of localization.
     */
    private String title;

    /**
     * The HTTP status code generated by the origin server for this occurrence
     * of the problem.
     */
    private Integer status;

    /**
     * An human readable explanation specific to this occurrence of the
     * problem.
     */
    private String detail;

    /**
     * An absolute URI that identifies the specific occurrence of the problem.
     * It may or may not yield further information if dereferenced.
     */
    private URI instance;


    public ErrorMessage(ErrorMessage orig) {
        this.type = orig.getType();
        this.title = orig.getTitle();
        this.status = orig.getStatus();
        this.detail = orig.getDetail();
        this.instance = orig.getInstance();
    }


    @JsonProperty
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }
}
