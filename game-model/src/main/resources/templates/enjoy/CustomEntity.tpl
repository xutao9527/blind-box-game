package #(entityPackageName);

import com.bbg.model.record.#(table.buildEntityClassName())Record;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "#(table.getComment())")
public class #(table.buildEntityClassName()) extends #(table.buildEntityClassName())Record {

}