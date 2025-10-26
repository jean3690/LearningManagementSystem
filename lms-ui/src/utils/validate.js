/**
 * 验证工具类
 * 提供常用的数据验证方法
 */
class ValidateUtils {
  /**
   * 验证是否为空
   * @param {any} value - 待验证的值
   * @returns {boolean} 是否为空
   */
  static isEmpty(value) {
    return (
      value === null ||
      value === undefined ||
      value === '' ||
      (Array.isArray(value) && value.length === 0) ||
      (typeof value === 'object' && Object.keys(value).length === 0)
    );
  }

  /**
   * 验证是否为邮箱
   * @param {string} email - 邮箱地址
   * @returns {boolean} 是否为有效邮箱
   */
  static isEmail(email) {
    const reg = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return reg.test(email);
  }

  /**
   * 验证是否为手机号
   * @param {string} phone - 手机号
   * @returns {boolean} 是否为有效手机号
   */
  static isPhone(phone) {
    const reg = /^1[3-9]\d{9}$/;
    return reg.test(phone);
  }

  /**
   * 验证是否为URL
   * @param {string} url - URL地址
   * @returns {boolean} 是否为有效URL
   */
  static isUrl(url) {
    try {
      new URL(url);
      return true;
    } catch (e) {
      return false;
    }
  }

  /**
   * 验证是否为身份证号
   * @param {string} idCard - 身份证号
   * @returns {boolean} 是否为有效身份证号
   */
  static isIdCard(idCard) {
    const reg = /(^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
    return reg.test(idCard);
  }

  /**
   * 验证是否为IP地址
   * @param {string} ip - IP地址
   * @returns {boolean} 是否为有效IP地址
   */
  static isIP(ip) {
    const reg = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
    return reg.test(ip);
  }

  /**
   * 验证是否为数字
   * @param {any} value - 待验证的值
   * @returns {boolean} 是否为数字
   */
  static isNumber(value) {
    return !isNaN(parseFloat(value)) && isFinite(value);
  }

  /**
   * 验证是否为整数
   * @param {any} value - 待验证的值
   * @returns {boolean} 是否为整数
   */
  static isInteger(value) {
    return Number.isInteger(Number(value));
  }

  /**
   * 验证是否为正数
   * @param {any} value - 待验证的值
   * @returns {boolean} 是否为正数
   */
  static isPositive(value) {
    return this.isNumber(value) && Number(value) > 0;
  }

  /**
   * 验证是否为负数
   * @param {any} value - 待验证的值
   * @returns {boolean} 是否为负数
   */
  static isNegative(value) {
    return this.isNumber(value) && Number(value) < 0;
  }

  /**
   * 验证是否为中文
   * @param {string} value - 待验证的值
   * @returns {boolean} 是否为中文
   */
  static isChinese(value) {
    const reg = /^[\u4e00-\u9fa5]+$/;
    return reg.test(value);
  }

  /**
   * 验证是否为英文字母
   * @param {string} value - 待验证的值
   * @returns {boolean} 是否为英文字母
   */
  static isEnglish(value) {
    const reg = /^[a-zA-Z]+$/;
    return reg.test(value);
  }

  /**
   * 验证密码强度
   * @param {string} password - 密码
   * @returns {Object} 密码强度信息
   */
  static checkPasswordStrength(password) {
    let strength = 0;
    const feedback = [];

    // 长度检查
    if (password.length < 6) {
      feedback.push('密码长度至少6位');
    } else if (password.length >= 6 && password.length < 10) {
      strength += 1;
      feedback.push('密码长度一般');
    } else if (password.length >= 10 && password.length < 15) {
      strength += 2;
      feedback.push('密码长度良好');
    } else {
      strength += 3;
      feedback.push('密码长度优秀');
    }

    // 包含数字
    if (/\d/.test(password)) {
      strength += 1;
    } else {
      feedback.push('建议包含数字');
    }

    // 包含小写字母
    if (/[a-z]/.test(password)) {
      strength += 1;
    } else {
      feedback.push('建议包含小写字母');
    }

    // 包含大写字母
    if (/[A-Z]/.test(password)) {
      strength += 1;
    } else {
      feedback.push('建议包含大写字母');
    }

    // 包含特殊字符
    if (/[^\w\s]/.test(password)) {
      strength += 1;
    } else {
      feedback.push('建议包含特殊字符');
    }

    // 根据强度返回级别
    let level = '弱';
    if (strength >= 6) {
      level = '强';
    } else if (strength >= 4) {
      level = '中';
    }

    return {
      strength,
      level,
      feedback
    };
  }

  /**
   * 验证银行卡号
   * @param {string} cardNumber - 银行卡号
   * @returns {boolean} 是否为有效银行卡号
   */
  static isBankCard(cardNumber) {
    const reg = /^[0-9]{16,19}$/;
    return reg.test(cardNumber);
  }

  /**
   * 验证是否为日期
   * @param {string} date - 日期字符串
   * @returns {boolean} 是否为有效日期
   */
  static isDate(date) {
    return !isNaN(Date.parse(date));
  }

  /**
   * 验证文件扩展名
   * @param {string} filename - 文件名
   * @param {string|Array<string>} extensions - 允许的扩展名，如 'jpg' 或 ['jpg', 'png']
 * @returns {boolean} 是否为允许的扩展名
   */
  static isFileExtension(filename, extensions) {
    if (!filename || typeof filename !== 'string') return false;
    
    const ext = filename.split('.').pop().toLowerCase();
    
    if (Array.isArray(extensions)) {
      return extensions.some(e => e.toLowerCase() === ext);
    } else {
      return ext === extensions.toLowerCase();
    }
  }

  /**
   * 验证是否为JSON字符串
   * @param {string} str - 字符串
   * @returns {boolean} 是否为有效JSON字符串
   */
  static isJSON(str) {
    try {
      JSON.parse(str);
      return true;
    } catch (e) {
      return false;
    }
  }
}

export default ValidateUtils;