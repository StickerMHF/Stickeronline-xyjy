export function newMessages() {
  return {
    default: '字段 %s 验证出错',
    required: '%s 为必填',
    enum: '%s 必须为其中之一 %s',
    whitespace: '%s 必须为空',
    date: {
      format: '%s 日期 %s 是无效的格式 %s',
      parse: '%s 日期不能被转换, %s 无效的 ',
      invalid: '%s 日期 %s 是无效的',
    },
    types: {
      string: '%s 不是一个有效的 %s',
      method: '%s 不是一个有效的 %s (function)',
      array: '%s 不是一个有效的 %s',
      object: '%s 不是一个有效的 %s',
      number: '%s 不是一个有效的 %s',
      date: '%s 不是一个有效的 %s',
      boolean: '%s 不是一个有效的 %s',
      integer: '%s 不是一个有效的 %s',
      float: '%s 不是一个 %s',
      regexp: '%s 不是一个有效的 %s',
      email: '%s 不是一个有效的 %s',
      url: '%s 不是一个有效的 %s',
      hex: '%s 不是一个有效的 %s',
    },
    string: {
      len: '%s 字符长度必须为 %s ',
      min: '%s 字符长度最少 %s ',
      max: '%s 字符长度必须比 %s 长',
      range: '%s 字符长度必须在 %s 和 %s 之间',
    },
    number: {
      len: '%s 必须等于 %s',
      min: '%s 不能小于 %s',
      max: '%s 不能大于 %s',
      range: '%s 必须在 %s 和 %s 之间',
    },
    array: {
      len: '%s 长度必须为 %s ',
      min: '%s 长度不小于 %s ',
      max: '%s 长度不大于 %s ',
      range: '%s 长度必须在 %s 和 %s 之间',
    },
    pattern: {
      mismatch: '%s 值 %s 与规则不相等 %s',
    }
  };
}

export const messages = newMessages();