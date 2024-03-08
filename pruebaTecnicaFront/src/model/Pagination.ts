export interface Pagination{
    page: number;
    limit: number;
    dataFilter:Filter[];
    columnSort:string;
}
export interface Filter{
      value:String;
      field:String;
      filterType:FilterType;
}
export enum FilterType{
    CONTAINS,
    NOT_CONTAINS,
    EQUALS,
    IS_NULL,
    NOT_NULL,
    BETWEEN,
    NOT_BETWEEN,
    ON_DATE,
    NOT_ON_DATE
}