package net.combatserver.protobuf {
	import com.google.protobuf.*;
	import com.google.protobuf.fieldDescriptors.*;
	import flash.utils.Endian;
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	import flash.errors.IOError;
	import net.combatserver.protobuf.Region;
	// @@protoc_insertion_point(imports)

	// @@protoc_insertion_point(class_metadata)
	public dynamic final class RegionListNoticeData extends com.google.protobuf.Message {
		/**
		 *  @private
		 */
		public static const REGION:RepeatedFieldDescriptor$TYPE_MESSAGE = new RepeatedFieldDescriptor$TYPE_MESSAGE("net.combatserver.protobuf.RegionListNoticeData.region", "region", (1 << 3) | com.google.protobuf.WireType.LENGTH_DELIMITED, net.combatserver.protobuf.Region);

		[ArrayElementType("net.combatserver.protobuf.Region")]
		public var region:Array = [];

		/**
		 *  @private
		 */
		override public final function writeToBuffer(output:com.google.protobuf.WritingBuffer):void {
			for (var region$index:uint = 0; region$index < this.region.length; ++region$index) {
				com.google.protobuf.WriteUtils.writeTag(output, com.google.protobuf.WireType.LENGTH_DELIMITED, 1);
				com.google.protobuf.WriteUtils.write$TYPE_MESSAGE(output, this.region[region$index]);
			}
			for (var fieldKey:* in this) {
				super.writeUnknown(output, fieldKey);
			}
		}

		/**
		 *  @private
		 */
		override public final function readFromSlice(input:flash.utils.IDataInput, bytesAfterSlice:uint):void {
			while (input.bytesAvailable > bytesAfterSlice) {
				var tag:uint = com.google.protobuf.ReadUtils.read$TYPE_UINT32(input);
				switch (tag >> 3) {
				case 1:
					this.region.push(com.google.protobuf.ReadUtils.read$TYPE_MESSAGE(input, new net.combatserver.protobuf.Region()));
					break;
				default:
					super.readUnknown(input, tag);
					break;
				}
			}
		}

	}
}
