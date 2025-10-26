/**
 * 存储工具类
 * 封装了localStorage和sessionStorage的操作
 */
class StorageUtils {
  /**
   * 设置localStorage
   * @param {string} key - 键名
   * @param {any} value - 值
   * @param {number} [expire] - 过期时间（毫秒），可选
   */
  static setLocal(key, value, expire) {
    const data = {
      value,
      expire: expire ? Date.now() + expire : null
    };
    localStorage.setItem(key, JSON.stringify(data));
  }

  /**
   * 获取localStorage
   * @param {string} key - 键名
   * @param {any} [defaultValue] - 默认值，可选
   * @returns {any} 存储的值或默认值
   */
  static getLocal(key, defaultValue = null) {
    try {
      const item = localStorage.getItem(key);
      if (!item) return defaultValue;
      
      const data = JSON.parse(item);
      
      // 检查是否过期
      if (data.expire && Date.now() > data.expire) {
        localStorage.removeItem(key);
        return defaultValue;
      }
      
      return data.value;
    } catch (e) {
      console.error('获取localStorage失败:', e);
      return defaultValue;
    }
  }

  /**
   * 删除localStorage
   * @param {string} key - 键名
   */
  static removeLocal(key) {
    localStorage.removeItem(key);
  }

  /**
   * 清空所有localStorage
   */
  static clearLocal() {
    localStorage.clear();
  }

  /**
   * 设置sessionStorage
   * @param {string} key - 键名
   * @param {any} value - 值
   * @param {number} [expire] - 过期时间（毫秒），可选
   */
  static setSession(key, value, expire) {
    const data = {
      value,
      expire: expire ? Date.now() + expire : null
    };
    sessionStorage.setItem(key, JSON.stringify(data));
  }

  /**
   * 获取sessionStorage
   * @param {string} key - 键名
   * @param {any} [defaultValue] - 默认值，可选
   * @returns {any} 存储的值或默认值
   */
  static getSession(key, defaultValue = null) {
    try {
      const item = sessionStorage.getItem(key);
      if (!item) return defaultValue;
      
      const data = JSON.parse(item);
      
      // 检查是否过期
      if (data.expire && Date.now() > data.expire) {
        sessionStorage.removeItem(key);
        return defaultValue;
      }
      
      return data.value;
    } catch (e) {
      console.error('获取sessionStorage失败:', e);
      return defaultValue;
    }
  }

  /**
   * 删除sessionStorage
   * @param {string} key - 键名
   */
  static removeSession(key) {
    sessionStorage.removeItem(key);
  }

  /**
   * 清空所有sessionStorage
   */
  static clearSession() {
    sessionStorage.clear();
  }

  /**
   * 获取所有localStorage的键名
   * @returns {Array<string>} 键名数组
   */
  static getLocalKeys() {
    const keys = [];
    for (let i = 0; i < localStorage.length; i++) {
      keys.push(localStorage.key(i));
    }
    return keys;
  }

  /**
   * 获取所有sessionStorage的键名
   * @returns {Array<string>} 键名数组
   */
  static getSessionKeys() {
    const keys = [];
    for (let i = 0; i < sessionStorage.length; i++) {
      keys.push(sessionStorage.key(i));
    }
    return keys;
  }

  /**
   * 获取localStorage的大小（字节）
   * @returns {number} 大小（字节）
   */
  static getLocalSize() {
    let size = 0;
    for (let key in localStorage) {
      if (localStorage.hasOwnProperty(key)) {
        size += localStorage[key].length + key.length;
      }
    }
    return size;
  }

  /**
   * 获取sessionStorage的大小（字节）
   * @returns {number} 大小（字节）
   */
  static getSessionSize() {
    let size = 0;
    for (let key in sessionStorage) {
      if (sessionStorage.hasOwnProperty(key)) {
        size += sessionStorage[key].length + key.length;
      }
    }
    return size;
  }
}

export default StorageUtils;