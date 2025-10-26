/**
 * 数组处理工具类
 * 提供常用的数组处理方法
 */
class ArrayUtils {
  /**
   * 数组去重
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于去重的键名或比较函数
   * @returns {Array} 去重后的数组
   */
  static unique(arr, key) {
    if (!Array.isArray(arr)) return [];
    
    if (!key) {
      // 基本类型去重
      return [...new Set(arr)];
    }
    
    if (typeof key === 'function') {
      // 使用函数比较
      const seen = new Set();
      return arr.filter(item => {
        const computed = key(item);
        return seen.has(computed) ? false : (seen.add(computed), true);
      });
    } else {
      // 使用对象属性比较
      const seen = new Set();
      return arr.filter(item => {
        const computed = item[key];
        return seen.has(computed) ? false : (seen.add(computed), true);
      });
    }
  }

  /**
   * 数组分组
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于分组的键名或分组函数
   * @returns {Object} 分组后的对象
   */
  static groupBy(arr, key) {
    if (!Array.isArray(arr)) return {};
    
    return arr.reduce((groups, item) => {
      const group = typeof key === 'function' ? key(item) : item[key];
      groups[group] = groups[group] || [];
      groups[group].push(item);
      return groups;
    }, {});
  }

  /**
   * 数组排序
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于排序的键名或比较函数
   * @param {boolean} desc - 是否降序，默认为false
   * @returns {Array} 排序后的数组
   */
  static sortBy(arr, key, desc = false) {
    if (!Array.isArray(arr)) return [];
    
    const sorted = [...arr];
    
    if (typeof key === 'function') {
      sorted.sort((a, b) => {
        const result = key(a) - key(b);
        return desc ? -result : result;
      });
    } else if (typeof key === 'string') {
      sorted.sort((a, b) => {
        if (a[key] < b[key]) return desc ? 1 : -1;
        if (a[key] > b[key]) return desc ? -1 : 1;
        return 0;
      });
    } else {
      sorted.sort((a, b) => {
        if (a < b) return desc ? 1 : -1;
        if (a > b) return desc ? -1 : 1;
        return 0;
      });
    }
    
    return sorted;
  }

  /**
   * 数组分页
   * @param {Array} arr - 数组
   * @param {number} page - 当前页码，从1开始
   * @param {number} pageSize - 每页数量
   * @returns {Object} 分页结果
   */
  static paginate(arr, page = 1, pageSize = 10) {
    if (!Array.isArray(arr)) return { data: [], total: 0, page: 1, pageSize };
    
    const total = arr.length;
    const totalPages = Math.ceil(total / pageSize);
    const currentPage = Math.max(1, Math.min(page, totalPages));
    const startIndex = (currentPage - 1) * pageSize;
    const endIndex = startIndex + pageSize;
    const data = arr.slice(startIndex, endIndex);
    
    return {
      data,
      total,
      page: currentPage,
      pageSize,
      totalPages
    };
  }

  /**
   * 数组扁平化
   * @param {Array} arr - 数组
   * @param {number} depth - 扁平化深度，默认为Infinity
   * @returns {Array} 扁平化后的数组
   */
  static flatten(arr, depth = Infinity) {
    if (!Array.isArray(arr)) return [];
    return depth > 0 ? arr.reduce((acc, val) => acc.concat(Array.isArray(val) ? this.flatten(val, depth - 1) : val), []) : arr.slice();
  }

  /**
   * 数组差集
   * @param {Array} arr1 - 第一个数组
   * @param {Array} arr2 - 第二个数组
   * @param {string|Function} key - 用于比较的键名或比较函数
   * @returns {Array} 差集数组
   */
  static difference(arr1, arr2, key) {
    if (!Array.isArray(arr1) || !Array.isArray(arr2)) return [];
    
    if (!key) {
      // 基本类型差集
      return arr1.filter(item => !arr2.includes(item));
    }
    
    if (typeof key === 'function') {
      const arr2Values = arr2.map(key);
      return arr1.filter(item => !arr2Values.includes(key(item)));
    } else {
      const arr2Values = arr2.map(item => item[key]);
      return arr1.filter(item => !arr2Values.includes(item[key]));
    }
  }

  /**
   * 数组交集
   * @param {Array} arr1 - 第一个数组
   * @param {Array} arr2 - 第二个数组
   * @param {string|Function} key - 用于比较的键名或比较函数
   * @returns {Array} 交集数组
   */
  static intersection(arr1, arr2, key) {
    if (!Array.isArray(arr1) || !Array.isArray(arr2)) return [];
    
    if (!key) {
      // 基本类型交集
      return arr1.filter(item => arr2.includes(item));
    }
    
    if (typeof key === 'function') {
      const arr2Values = arr2.map(key);
      return arr1.filter(item => arr2Values.includes(key(item)));
    } else {
      const arr2Values = arr2.map(item => item[key]);
      return arr1.filter(item => arr2Values.includes(item[key]));
    }
  }

  /**
   * 数组并集
   * @param {Array} arr1 - 第一个数组
   * @param {Array} arr2 - 第二个数组
   * @param {string|Function} key - 用于去重的键名或比较函数
   * @returns {Array} 并集数组
   */
  static union(arr1, arr2, key) {
    if (!Array.isArray(arr1) || !Array.isArray(arr2)) return [];
    return this.unique([...arr1, ...arr2], key);
  }

  /**
   * 数组随机采样
   * @param {Array} arr - 数组
   * @param {number} count - 采样数量
   * @returns {Array} 采样结果
   */
  static sample(arr, count = 1) {
    if (!Array.isArray(arr) || count <= 0) return [];
    
    const shuffled = [...arr].sort(() => 0.5 - Math.random());
    return shuffled.slice(0, Math.min(count, arr.length));
  }

  /**
   * 数组分块
   * @param {Array} arr - 数组
   * @param {number} size - 每块的大小
   * @returns {Array} 分块后的二维数组
   */
  static chunk(arr, size = 1) {
    if (!Array.isArray(arr) || size <= 0) return [];
    
    const chunks = [];
    for (let i = 0; i < arr.length; i += size) {
      chunks.push(arr.slice(i, i + size));
    }
    return chunks;
  }

  /**
   * 数组求和
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于求和的键名或计算函数
   * @returns {number} 求和结果
   */
  static sum(arr, key) {
    if (!Array.isArray(arr)) return 0;
    
    if (!key) {
      return arr.reduce((sum, item) => sum + (Number(item) || 0), 0);
    }
    
    if (typeof key === 'function') {
      return arr.reduce((sum, item) => sum + (Number(key(item)) || 0), 0);
    } else {
      return arr.reduce((sum, item) => sum + (Number(item[key]) || 0), 0);
    }
  }

  /**
   * 数组求平均值
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于求平均值的键名或计算函数
   * @returns {number} 平均值
   */
  static average(arr, key) {
    if (!Array.isArray(arr) || arr.length === 0) return 0;
    return this.sum(arr, key) / arr.length;
  }

  /**
   * 数组求最大值
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于比较的键名或计算函数
   * @returns {any} 最大值
   */
  static max(arr, key) {
    if (!Array.isArray(arr) || arr.length === 0) return undefined;
    
    if (!key) {
      return Math.max(...arr);
    }
    
    if (typeof key === 'function') {
      return arr.reduce((max, item) => {
        const value = key(item);
        return value > max.value ? { item, value } : max;
      }, { item: arr[0], value: key(arr[0]) }).item;
    } else {
      return arr.reduce((max, item) => {
        const value = item[key];
        return value > max.value ? { item, value } : max;
      }, { item: arr[0], value: arr[0][key] }).item;
    }
  }

  /**
   * 数组求最小值
   * @param {Array} arr - 数组
   * @param {string|Function} key - 用于比较的键名或计算函数
   * @returns {any} 最小值
   */
  static min(arr, key) {
    if (!Array.isArray(arr) || arr.length === 0) return undefined;
    
    if (!key) {
      return Math.min(...arr);
    }
    
    if (typeof key === 'function') {
      return arr.reduce((min, item) => {
        const value = key(item);
        return value < min.value ? { item, value } : min;
      }, { item: arr[0], value: key(arr[0]) }).item;
    } else {
      return arr.reduce((min, item) => {
        const value = item[key];
        return value < min.value ? { item, value } : min;
      }, { item: arr[0], value: arr[0][key] }).item;
    }
  }

  /**
   * 打乱数组
   * @param {Array} arr - 数组
   * @returns {Array} 打乱后的数组
   */
  static shuffle(arr) {
    if (!Array.isArray(arr)) return [];
    
    const result = [...arr];
    for (let i = result.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [result[i], result[j]] = [result[j], result[i]];
    }
    return result;
  }

  /**
   * 数组转换为树形结构
   * @param {Array} arr - 数组
   * @param {string} idKey - ID键名，默认为 'id'
   * @param {string} parentIdKey - 父ID键名，默认为 'parentId'
   * @param {string} childrenKey - 子节点键名，默认为 'children'
   * @returns {Array} 树形结构数组
   */
  static toTree(arr, idKey = 'id', parentIdKey = 'parentId', childrenKey = 'children') {
    if (!Array.isArray(arr)) return [];
    
    const result = [];
    const map = {};
    
    // 创建映射
    arr.forEach(item => {
      map[item[idKey]] = { ...item, [childrenKey]: [] };
    });
    
    // 构建树形结构
    arr.forEach(item => {
      const id = item[idKey];
      const parentId = item[parentIdKey];
      const node = map[id];
      
      if (parentId && map[parentId]) {
        map[parentId][childrenKey].push(node);
      } else {
        result.push(node);
      }
    });
    
    return result;
  }

  /**
   * 树形结构转换为数组
   * @param {Array} tree - 树形结构数组
   * @param {string} childrenKey - 子节点键名，默认为 'children'
   * @returns {Array} 扁平化数组
   */
  static fromTree(tree, childrenKey = 'children') {
    if (!Array.isArray(tree)) return [];
    
    const result = [];
    
    const traverse = (nodes, parent = null) => {
      nodes.forEach(node => {
        const { [childrenKey]: children, ...rest } = node;
        result.push({ ...rest, parent });
        if (children && children.length > 0) {
          traverse(children, node[idKey] || null);
        }
      });
    };
    
    traverse(tree);
    return result;
  }
}

export default ArrayUtils;