// Generated with pvdc v0.7.3-SNAPSHOT

/**
 * The service call type is a base distinction of what the message means, and
 * lets the server or client select the proper message to be serialized or
 * deserialized from the service method descriptor.
 */
export enum PServiceCallType {
    /**
     * The service method request.
     */
    CALL = 1,
    /**
     * Normal method call reply. This includes declared exceptions on the
     * service method.
     */
    REPLY = 2,
    /**
     * An application exception, i.e. either a non-declared exception, or a
     * providence service or serialization exception. This is also happens when
     * such exceptions happen on the server side, it will try to send an
     * application exception back to the client.
     */
    EXCEPTION = 3,
    /**
     * A one-way call is a request that does not expect a response at all. The
     * client will return as soon as the request is sent.
     */
    ONEWAY = 4
}

/**
 * Get the value of the enum, given value or name
 */
namespace PServiceCallType {
    export function valueOf(id:any, opt_keepNumeric?:boolean):number {
        switch(id) {
            case 1:
            case '1':
            case 'call':
                return PServiceCallType.CALL;
            case 2:
            case '2':
            case 'reply':
                return PServiceCallType.REPLY;
            case 3:
            case '3':
            case 'exception':
                return PServiceCallType.EXCEPTION;
            case 4:
            case '4':
            case 'oneway':
                return PServiceCallType.ONEWAY;
            default:
                if (opt_keepNumeric && 'number' === typeof(id)) {
                    return id;
                }
                return null;
        }
    }

    /**
     * Get the string name of the enum value.
     */
    export function nameOf(value:any, opt_keepNumeric?:boolean):string {
        switch(value) {
            case 1:
                return 'call';
            case 2:
                return 'reply';
            case 3:
                return 'exception';
            case 4:
                return 'oneway';
            default:
                if (!!opt_keepNumeric) return String(value);
                return null;
        }
    }

}
/**
 * General type of exception on the application level.
 */
export enum PApplicationExceptionType {
    /**
     * Unknown or unidentified exception, should usually not be uased.
     */
    UNKNOWN = 0,
    /**
     * There is no such method defined on the service.
     */
    UNKNOWN_METHOD = 1,
    /**
     * The service call type does not make sense, or is plain wrong, e.g.
     * sending &#39;reply&#39; or &#39;exception&#39; as the request.
     */
    INVALID_MESSAGE_TYPE = 2,
    /**
     * The response came back with a non-matching method name.
     */
    WRONG_METHOD_NAME = 3,
    /**
     * The response came back with a non-matching sequence ID.
     */
    BAD_SEQUENCE_ID = 4,
    /**
     * The response did not have a defined non-null result.
     * 
     * NOTE: This is the default behavior from thrift, and we may need to keep
     * it this way as long as thrift compatibility is expected.
     */
    MISSING_RESULT = 5,
    /**
     * The service handler or client handler experienced internal problem.
     */
    INTERNAL_ERROR = 6,
    /**
     * Serialization or deserialization failed or the deserialized content was
     * not valid for the requested message.
     * 
     * NOTE: In providence this is valid for server (processor) side
     * serialization errors.
     */
    PROTOCOL_ERROR = 7,
    /**
     * NOTE: This value is apparently not in use in thrift.
     */
    INVALID_TRANSFORM = 8,
    /**
     * The requested protocol (or version) is not supported.
     */
    INVALID_PROTOCOL = 9,
    /**
     * NOTE: This value is apparently not in use in thrift.
     */
    UNSUPPORTED_CLIENT_TYPE = 10
}

/**
 * Get the value of the enum, given value or name
 */
namespace PApplicationExceptionType {
    export function valueOf(id:any, opt_keepNumeric?:boolean):number {
        switch(id) {
            case 0:
            case '0':
            case 'UNKNOWN':
                return PApplicationExceptionType.UNKNOWN;
            case 1:
            case '1':
            case 'UNKNOWN_METHOD':
                return PApplicationExceptionType.UNKNOWN_METHOD;
            case 2:
            case '2':
            case 'INVALID_MESSAGE_TYPE':
                return PApplicationExceptionType.INVALID_MESSAGE_TYPE;
            case 3:
            case '3':
            case 'WRONG_METHOD_NAME':
                return PApplicationExceptionType.WRONG_METHOD_NAME;
            case 4:
            case '4':
            case 'BAD_SEQUENCE_ID':
                return PApplicationExceptionType.BAD_SEQUENCE_ID;
            case 5:
            case '5':
            case 'MISSING_RESULT':
                return PApplicationExceptionType.MISSING_RESULT;
            case 6:
            case '6':
            case 'INTERNAL_ERROR':
                return PApplicationExceptionType.INTERNAL_ERROR;
            case 7:
            case '7':
            case 'PROTOCOL_ERROR':
                return PApplicationExceptionType.PROTOCOL_ERROR;
            case 8:
            case '8':
            case 'INVALID_TRANSFORM':
                return PApplicationExceptionType.INVALID_TRANSFORM;
            case 9:
            case '9':
            case 'INVALID_PROTOCOL':
                return PApplicationExceptionType.INVALID_PROTOCOL;
            case 10:
            case '10':
            case 'UNSUPPORTED_CLIENT_TYPE':
                return PApplicationExceptionType.UNSUPPORTED_CLIENT_TYPE;
            default:
                if (opt_keepNumeric && 'number' === typeof(id)) {
                    return id;
                }
                return null;
        }
    }

    /**
     * Get the string name of the enum value.
     */
    export function nameOf(value:any, opt_keepNumeric?:boolean):string {
        switch(value) {
            case 0:
                return 'UNKNOWN';
            case 1:
                return 'UNKNOWN_METHOD';
            case 2:
                return 'INVALID_MESSAGE_TYPE';
            case 3:
                return 'WRONG_METHOD_NAME';
            case 4:
                return 'BAD_SEQUENCE_ID';
            case 5:
                return 'MISSING_RESULT';
            case 6:
                return 'INTERNAL_ERROR';
            case 7:
                return 'PROTOCOL_ERROR';
            case 8:
                return 'INVALID_TRANSFORM';
            case 9:
                return 'INVALID_PROTOCOL';
            case 10:
                return 'UNSUPPORTED_CLIENT_TYPE';
            default:
                if (!!opt_keepNumeric) return String(value);
                return null;
        }
    }

}
export class PApplicationException {
    private _message: string;
    private _id: PApplicationExceptionType;

    /**
     * Base exception thrown on non-declared exceptions on a service call, and
     * other server-side service call issues.
     */
    constructor(opt_json?:any) {
        this._message = null;
        this._id = null;

        if ('string' === typeof(opt_json)) {
            opt_json = JSON.parse(opt_json);
        }
        if ('object' === typeof(opt_json)) {
            for (var key in opt_json) {
                if (opt_json.hasOwnProperty(key)) {
                    switch (key) {
                        case '1':
                        case 'message':
                            this._message = String(opt_json[key]);
                            break;
                        case '2':
                        case 'id':
                            this._id = PApplicationExceptionType.valueOf(opt_json[key], true);
                            break;
                        default:
                            break;
                    }
                }
            }
        } else if ('undefined' !== typeof(opt_json)){
            throw 'Bad json input type: ' + typeof(opt_json);
        }
    }

    /**
     * Exception message.
     */
    getMessage():string {
        if (this._message === null) {
            return "";
        } else {
            return this._message;
        }
    }

    /**
     * Exception message.
     */
    setMessage(value?:string):void {
        if (value !== null && value !== undefined) {
            this._message = value;
        } else {
            this._message = null;
        }
    }

    /**
     * The application exception type.
     */
    getId():PApplicationExceptionType {
        if (this._id === null) {
            return PApplicationExceptionType.UNKNOWN;
        } else {
            return this._id;
        }
    }

    /**
     * The application exception type.
     */
    setId(value?:PApplicationExceptionType):void {
        if (value !== null && value !== undefined) {
            this._id = value;
        } else {
            this._id = null;
        }
    }

    /**
     * Make a JSON compatible object representation of the message.
     */
    toJson(opt_named?:boolean): any {
        var obj : { [key:string]: any } = {};
        if (opt_named) {
            if (this._message !== null) {
                obj['message'] = this._message;
            }
            if (this._id !== null) {
                obj['id'] = PApplicationExceptionType.nameOf(this._id, true);
            }
        } else {
            if (this._message !== null) {
                obj['1'] = this._message;
            }
            if (this._id !== null) {
                obj['2'] = this._id;
            }
        }
        return obj;
    }

    /**
     * Make a JSON string representation of the message.
     */
    toJsonString(opt_named?:boolean):string {
        return JSON.stringify(this.toJson(opt_named));
    }

    /**
     * String representation of the message.
     */
    toString():string {
        return 'PApplicationException' + JSON.stringify(this.toJson(true));
    }

}