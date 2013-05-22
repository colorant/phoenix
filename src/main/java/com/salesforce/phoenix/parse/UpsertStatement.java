/*******************************************************************************
 * Copyright (c) 2013, Salesforce.com, Inc.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *     Neither the name of Salesforce.com nor the names of its contributors may 
 *     be used to endorse or promote products derived from this software without 
 *     specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, 
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package com.salesforce.phoenix.parse;

import java.util.*;

import com.google.common.collect.ImmutableList;

public class UpsertStatement extends MutationStatement {
    private final List<ParseNode> columns;
    private final boolean isIncrease;
    private final List<ParseNode> values;
    private final SelectStatement select;
    private final List<ColumnDef> dynColumns;

    public UpsertStatement(TableName table, List<ParseNode> columns, boolean isIncrease, List<ParseNode> values, SelectStatement select, int bindCount) {
        super(table, bindCount);
        this.columns = columns == null ? Collections.<ParseNode>emptyList() : columns;
        this.isIncrease = isIncrease;
        this.values = values;
        this.select = select;
        List<ColumnDef> dynamicColumns = new ArrayList<ColumnDef>();
        for(ParseNode pn:this.columns){
          if(pn instanceof DynamicColumnParseNode){
            dynamicColumns.add(((DynamicColumnParseNode)pn).getColumnDef());
          }
        }
        this.dynColumns = ImmutableList.copyOf(dynamicColumns);
    }

    public List<ParseNode> getColumns() {
        return columns;
    }

    public boolean isIncrease() {
        return isIncrease;
    }
    public List<ParseNode> getValues() {
        return values;
    }

    public SelectStatement getSelect() {
        return select;
    }

    public List<ColumnDef> getDynColumns() {
      return dynColumns;
    }

    public boolean onlyDynamic() {
      return dynColumns.size()==columns.size();
    }
}
