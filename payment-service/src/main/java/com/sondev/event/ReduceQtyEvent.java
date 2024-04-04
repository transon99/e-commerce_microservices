package com.sondev.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReduceQtyEvent {
    List<ReduceQtyData> reduceQtyDataList;
}
