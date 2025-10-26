import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/zh-cn';

// 配置dayjs
dayjs.extend(relativeTime);
dayjs.locale('zh-cn');

/**
 * 日期工具类
 */
class DateUtils {
  /**
   * 格式化日期
   * @param {Date|string|number} date - 日期
   * @param {string} format - 格式化模板，默认 'YYYY-MM-DD HH:mm:ss'
   * @returns {string} 格式化后的日期字符串
   */
  static format(date, format = 'YYYY-MM-DD HH:mm:ss') {
    return dayjs(date).format(format);
  }

  /**
   * 获取相对时间
   * @param {Date|string|number} date - 日期
   * @returns {string} 相对时间字符串，如 "3分钟前"
   */
  static relativeTime(date) {
    return dayjs(date).fromNow();
  }

  /**
   * 获取当前日期
   * @param {string} format - 格式化模板，默认 'YYYY-MM-DD HH:mm:ss'
   * @returns {string} 当前日期字符串
   */
  static now(format = 'YYYY-MM-DD HH:mm:ss') {
    return dayjs().format(format);
  }

  /**
   * 获取今天开始时间
   * @returns {Date} 今天开始时间的Date对象
   */
  static startOfDay() {
    return dayjs().startOf('day').toDate();
  }

  /**
   * 获取今天结束时间
   * @returns {Date} 今天结束时间的Date对象
   */
  static endOfDay() {
    return dayjs().endOf('day').toDate();
  }

  /**
   * 获取本周开始时间
   * @returns {Date} 本周开始时间的Date对象
   */
  static startOfWeek() {
    return dayjs().startOf('week').toDate();
  }

  /**
   * 获取本周结束时间
   * @returns {Date} 本周结束时间的Date对象
   */
  static endOfWeek() {
    return dayjs().endOf('week').toDate();
  }

  /**
   * 获取本月开始时间
   * @returns {Date} 本月开始时间的Date对象
   */
  static startOfMonth() {
    return dayjs().startOf('month').toDate();
  }

  /**
   * 获取本月结束时间
   * @returns {Date} 本月结束时间的Date对象
   */
  static endOfMonth() {
    return dayjs().endOf('month').toDate();
  }

  /**
   * 计算两个日期之间的天数差
   * @param {Date|string|number} startDate - 开始日期
   * @param {Date|string|number} endDate - 结束日期
   * @returns {number} 天数差
   */
  static daysBetween(startDate, endDate) {
    return dayjs(endDate).diff(dayjs(startDate), 'day');
  }

  /**
   * 获取某月的天数
   * @param {number} year - 年份
   * @param {number} month - 月份 (1-12)
   * @returns {number} 该月的天数
   */
  static daysInMonth(year, month) {
    return dayjs(`${year}-${month}-01`).daysInMonth();
  }

  /**
   * 判断是否为今天
   * @param {Date|string|number} date - 日期
   * @returns {boolean} 是否为今天
   */
  static isToday(date) {
    return dayjs(date).isSame(dayjs(), 'day');
  }

  /**
   * 判断是否为昨天
   * @param {Date|string|number} date - 日期
   * @returns {boolean} 是否为昨天
   */
  static isYesterday(date) {
    return dayjs(date).isSame(dayjs().subtract(1, 'day'), 'day');
  }

  /**
   * 判断是否为本周
   * @param {Date|string|number} date - 日期
   * @returns {boolean} 是否为本周
   */
  static isThisWeek(date) {
    return dayjs(date).isSame(dayjs(), 'week');
  }

  /**
   * 判断是否为本月
   * @param {Date|string|number} date - 日期
   * @returns {boolean} 是否为本月
   */
  static isThisMonth(date) {
    return dayjs(date).isSame(dayjs(), 'month');
  }

  /**
   * 获取时间戳
   * @param {Date|string|number} date - 日期，默认为当前时间
   * @returns {number} 时间戳（毫秒）
   */
  static timestamp(date = new Date()) {
    return dayjs(date).valueOf();
  }

  /**
   * 将秒数转换为时分秒格式
   * @param {number} seconds - 秒数
   * @param {string} format - 格式化模板，默认 'HH:mm:ss'
   * @returns {string} 格式化后的时间字符串
   */
  static secondsToTime(seconds, format = 'HH:mm:ss') {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    
    return format
      .replace('HH', String(hours).padStart(2, '0'))
      .replace('mm', String(minutes).padStart(2, '0'))
      .replace('ss', String(secs).padStart(2, '0'));
  }
}

export default DateUtils;