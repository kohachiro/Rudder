package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.Property;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class PluginRequestData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const NAME:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.PluginRequestData.name", "name", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var name:String;

		/**
		 *  @private
		 */
		public static const ACTION:FieldDescriptor$TYPE_STRING = new FieldDescriptor$TYPE_STRING("net.combatserver.protobuf.PluginRequestData.action", "action", (2 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED);

		public var action:String;

		/**
		 *  @private
		 */
		public static const PROPERTIES:RepeatedFieldDescriptor$TYPE_MESSAGE = new RepeatedFieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.PluginRequestData.properties", "properties", (3 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.Property);

		[ArrayElementType("net.combatserver.protobuf.Property")]
		public var properties:Array = [];

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.name);
			com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 2);
			com.google.protobuf.WriteUtils.write$TYPE_STRING(output, this.action);
			for (var properties$index:uint = 0; properties$index < this.properties.length; ++properties$index) {
				com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 3);
				com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.properties[properties$index]);
			}
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			var name$count:uint = 0;
			var action$count:uint = 0;
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					if (name$count != 0) {
						throw new flash.errors.IOError('Bad data format: PluginRequestData.name cannot be set twice.');
					}
					++name$count;
					this.name = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 2:
					if (action$count != 0) {
						throw new flash.errors.IOError('Bad data format: PluginRequestData.action cannot be set twice.');
					}
					++action$count;
					this.action = com.google.protobuf.ReadUtils.read$TYPE_STRING(input);
					break;
				case 3:
					this.properties.push(com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, new net.combatserver.protobuf.Property()));
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}
