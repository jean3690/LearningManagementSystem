/**
 * 字符串处理工具类
 * 提供常用的字符串处理方法
 */
class StringUtils {
  /**
   * 首字母大写
   * @param {string} str - 字符串
   * @returns {string} 首字母大写的字符串
   */
  static capitalize(str) {
    if (!str || typeof str !== 'string') return '';
    return str.charAt(0).toUpperCase() + str.slice(1);
  }

  /**
   * 驼峰命名转换
   * @param {string} str - 字符串，如 "hello-world"
   * @returns {string} 驼峰命名的字符串，如 "helloWorld"
   */
  static toCamelCase(str) {
    if (!str || typeof str !== 'string') return '';
    return str.replace(/[-_]([a-z])/g, (match, letter) => letter.toUpperCase());
  }

  /**
   * 短横线命名转换
   * @param {string} str - 字符串，如 "helloWorld"
   * @returns {string} 短横线命名的字符串，如 "hello-world"
   */
  static toKebabCase(str) {
    if (!str || typeof str !== 'string') return '';
    return str.replace(/([A-Z])/g, '-$1').toLowerCase();
  }

  /**
   * 下划线命名转换
   * @param {string} str - 字符串，如 "helloWorld"
   * @returns {string} 下划线命名的字符串，如 "hello_world"
   */
  static toSnakeCase(str) {
    if (!str || typeof str !== 'string') return '';
    return str.replace(/([A-Z])/g, '_$1').toLowerCase();
  }

  /**
   * 生成随机字符串
   * @param {number} length - 字符串长度，默认为8
   * @param {string} chars - 字符集，默认为字母和数字
   * @returns {string} 随机字符串
   */
  static random(length = 8, chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ') {
    let result = '';
    for (let i = 0; i < length; i++) {
      result += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return result;
  }

  /**
   * 截取字符串并添加省略号
   * @param {string} str - 字符串
   * @param {number} length - 最大长度
   * @param {string} suffix - 后缀，默认为 "..."
   * @returns {string} 截取后的字符串
   */
  static truncate(str, length, suffix = '...') {
    if (!str || typeof str !== 'string') return '';
    if (str.length <= length) return str;
    return str.slice(0, length - suffix.length) + suffix;
  }

  /**
   * 转义HTML特殊字符
   * @param {string} str - 字符串
   * @returns {string} 转义后的字符串
   */
  static escapeHtml(str) {
    if (!str || typeof str !== 'string') return '';
    const map = {
      '&': '&amp;',
      '<': '&lt;',
      '>': '&gt;',
      '"': '&quot;',
      "'": '&#039;'
    };
    return str.replace(/[&<>"']/g, m => map[m]);
  }

  /**
   * 反转义HTML特殊字符
   * @param {string} str - 字符串
   * @returns {string} 反转义后的字符串
   */
  static unescapeHtml(str) {
    if (!str || typeof str !== 'string') return '';
    const map = {
      '&amp;': '&',
      '&lt;': '<',
      '&gt;': '>',
      '&quot;': '"',
      '&#039;': "'"
    };
    return str.replace(/&amp;|&lt;|&gt;|&quot;|&#039;/g, m => map[m]);
  }

  /**
   * 移除字符串中的HTML标签
   * @param {string} str - 字符串
   * @returns {string} 移除标签后的字符串
   */
  static stripHtml(str) {
    if (!str || typeof str !== 'string') return '';
    return str.replace(/<[^>]*>/g, '');
  }

  /**
   * 格式化文件大小
   * @param {number} bytes - 字节数
   * @param {number} decimals - 小数位数，默认为2
   * @returns {string} 格式化后的文件大小
   */
  static formatFileSize(bytes, decimals = 2) {
    if (bytes === 0) return '0 Bytes';
    
    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  /**
   * 生成UUID
   * @returns {string} UUID字符串
   */
  static generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      const r = Math.random() * 16 | 0;
      const v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }

  /**
   * 字符串模板替换
   * @param {string} template - 模板字符串，如 "Hello, {name}!"
   * @param {Object} data - 数据对象，如 { name: "World" }
   * @returns {string} 替换后的字符串
   */
  static template(template, data) {
    if (!template || typeof template !== 'string') return '';
    if (!data || typeof data !== 'object') return template;
    
    return template.replace(/\{([^}]+)\}/g, (match, key) => {
      return data[key] !== undefined ? data[key] : match;
    });
  }

  /**
   * 计算字符串的字节长度
   * @param {string} str - 字符串
   * @returns {number} 字节长度
   */
  static byteLength(str) {
    if (!str || typeof str !== 'string') return 0;
    
    let length = 0;
    for (let i = 0; i < str.length; i++) {
      const code = str.charCodeAt(i);
      if (code > 127 || code === 94) {
        length += 2;
      } else {
        length += 1;
      }
    }
    return length;
  }

  /**
   * 将字符串转换为颜色值
   * @param {string} str - 字符串
   * @returns {string} 颜色值（十六进制）
   */
  static stringToColor(str) {
    if (!str || typeof str !== 'string') return '#000000';
    
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    
    let color = '#';
    for (let i = 0; i < 3; i++) {
      const value = (hash >> (i * 8)) & 0xFF;
      color += ('00' + value.toString(16)).substr(-2);
    }
    
    return color;
  }

  /**
   * 高亮关键词
   * @param {string} text - 原始文本
   * @param {string} keyword - 关键词
   * @param {string} className - 高亮样式类名，默认为 "highlight"
   * @returns {string} 高亮后的HTML字符串
   */
  static highlightKeyword(text, keyword, className = 'highlight') {
    if (!text || !keyword || typeof text !== 'string' || typeof keyword !== 'string') {
      return text;
    }
    
    const regex = new RegExp(`(${keyword})`, 'gi');
    return text.replace(regex, `<span class="${className}">$1</span>`);
  }

  /**
   * 格式化手机号
   * @param {string} phone - 手机号
   * @param {string} format - 格式，默认为 "xxx-xxxx-xxxx"
   * @returns {string} 格式化后的手机号
   */
  static formatPhone(phone, format = 'xxx-xxxx-xxxx') {
    if (!phone || typeof phone !== 'string' || phone.length !== 11) {
      return phone;
    }
    
    return format
      .replace(/x/g, (match, index) => phone[index] || match);
  }

  /**
   * 格式化身份证号
   * @param {string} idCard - 身份证号
   * @param {boolean} mask - 是否隐藏部分数字，默认为true
   * @returns {string} 格式化后的身份证号
   */
  static formatIdCard(idCard, mask = true) {
    if (!idCard || typeof idCard !== 'string') {
      return idCard;
    }
    
    if (mask && idCard.length === 18) {
      return idCard.replace(/(\d{6})\d{8}(\d{4})/, '$1********$2');
    } else if (mask && idCard.length === 15) {
      return idCard.replace(/(\d{6})\d{6}(\d{3})/, '$1******$2');
    }
    
    return idCard;
  }

  /**
   * 格式化银行卡号
   * @param {string} cardNumber - 银行卡号
   * @param {string} separator - 分隔符，默认为 " "
   * @param {boolean} mask - 是否隐藏部分数字，默认为true
   * @returns {string} 格式化后的银行卡号
   */
  static formatBankCard(cardNumber, separator = ' ', mask = true) {
    if (!cardNumber || typeof cardNumber !== 'string') {
      return cardNumber;
    }
    
    // 移除非数字字符
    const cleaned = cardNumber.replace(/\D/g, '');
    
    if (mask && cleaned.length > 4) {
      const start = cleaned.substring(0, 4);
      const end = cleaned.substring(cleaned.length - 4);
      const middle = '*'.repeat(cleaned.length - 8);
      
      const masked = start + middle + end;
      
      // 每4位添加一个分隔符
      return masked.replace(/(.{4})/g, `$1${separator}`).trim();
    } else {
      // 每4位添加一个分隔符
      return cleaned.replace(/(.{4})/g, `$1${separator}`).trim();
    }
  }
}

export default StringUtils;