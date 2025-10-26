import DateUtils from './date';
import StorageUtils from './storage';
import ValidateUtils from './validate';
import StringUtils from './string';
import ArrayUtils from './array';

/**
 * 工具类集合
 * 统一管理所有工具类
 */
const utils = {
  date: DateUtils,
  storage: StorageUtils,
  validate: ValidateUtils,
  string: StringUtils,
  array: ArrayUtils
};

export default utils;

/**
 * 按需导出各个工具类
 */
export { DateUtils, StorageUtils, ValidateUtils, StringUtils, ArrayUtils };